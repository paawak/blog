package com.swayam.demo.rmi.server.core.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.jini.id.Uuid;
import net.jini.id.UuidFactory;
import net.jini.io.MarshalInputStream;
import net.jini.io.MarshalOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sun.jini.jeri.internal.runtime.Util;
import com.swayam.demo.rmi.server.core.rmi.ServiceExporter;
import com.swayam.demo.rmi.shared.api.dto.BankDetail;
import com.swayam.demo.rmi.shared.api.dto.BankDetailGroups;
import com.swayam.demo.rmi.shared.api.service.BankDetailService;
import com.swayam.demo.rmi.shared.jini.servlet.ServletInboundRequest;
import com.swayam.demo.rmi.shared.jini.servlet.ServletOutboundRequest;

@Controller
public class RmiWebController implements ApplicationContextAware {

    private static final Logger LOG = LoggerFactory.getLogger(RmiWebController.class);

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @RequestMapping(value = ServletInboundRequest.INBOUND_CALL_URI + "{sequence}")
    public void handleInboundRequest(HttpServletRequest request, HttpServletResponse response, @PathVariable int sequence) throws IOException {
        writeToInboundRequest(response, sequence);
    }

    @RequestMapping(value = ServletOutboundRequest.OUTBOUND_CALL_URI + "{sequence}")
    public void handleOutboundRequest(HttpServletRequest request, HttpServletResponse response, @PathVariable int sequence) throws IOException {
        readFromOutboundRequest(request, sequence);
        writeToOutboundRequest(response, sequence);
    }

    @RequestMapping(value = ServiceExporter.REMOTE_METHOD_INVOCATION_URI)
    public void handleRemoteMethodInvocation(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // read input parameters
        Object result;
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        try (MarshalInputStream mis = new MarshalInputStream(request.getInputStream(), cl, false, cl, Collections.emptyList());) {
            String implClassName = (String) mis.readObject();
            Class<?> implClass = cl.loadClass(implClassName);
            long methodHash = mis.readLong();
            Method method = getMethod(implClass, methodHash);
            Object[] args = unmarshalArguments(method, mis);
            Object remoteImpl = applicationContext.getBean(implClass);
            result = method.invoke(remoteImpl, args);
        }

        try (MarshalOutputStream mos = new MarshalOutputStream(response.getOutputStream(), Collections.emptyList());) {
            mos.writeObject(result);
            mos.flush();
        }
    }

    private void writeToOutboundRequest(HttpServletResponse response, int sequence) throws IOException {
        LOG.info("writing output for the sequence: `{}`", sequence);

        if ((sequence == 1) || (sequence == 2)) {

            OutputStream os = response.getOutputStream();
            os.write(0x01);
            os.flush();
            os.close();

        } else if (sequence == 3) {

            BankDetailService bankDetailService = applicationContext.getBean("bankDetailServiceImpl", BankDetailService.class);
            Map<String, List<BankDetail>> result = bankDetailService.getBankDetails(BankDetailGroups.JOB);

            MarshalOutputStream mos = new MarshalOutputStream(response.getOutputStream(), Collections.emptyList());

            mos.writeObject(result);
            mos.flush();
            mos.close();

        } else {
            throw new UnsupportedOperationException("the sequence " + sequence + " is not supported");
        }
    }

    private void readFromOutboundRequest(HttpServletRequest request, int sequence) throws IOException {
        LOG.info("reading input for the sequence: `{}`", sequence);

        if (sequence == 1) {

            // read uuid
            InputStream is = request.getInputStream();
            Uuid uuid = UuidFactory.read(is);
            LOG.info("read uuid: {}", uuid);
            is.close();

        } else if (sequence == 2) {
            InputStream is = request.getInputStream();

            // read marshalling protocol version
            int marshallingProtocolVersion = is.read();
            LOG.info("read marshallingProtocolVersion: {}", marshallingProtocolVersion);

            // read integrity: 0 or 1
            int integrity = is.read();
            LOG.info("read integrity: {}", integrity);

            is.close();
        } else if (sequence == 3) {

            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            try (MarshalInputStream mis = new MarshalInputStream(request.getInputStream(), cl, false, cl, Collections.emptyList());) {
                // read method hash
                long methodHash = mis.readLong();
                LOG.info("read methodHash: {}", methodHash);
            }

        } else {
            throw new UnsupportedOperationException("the sequence " + sequence + " is not supported");
        }
    }

    private void writeToInboundRequest(HttpServletResponse response, int sequence) throws IOException {
        LOG.info("writing output for Inbound request for the sequence: `{}`", sequence);

        if (sequence == 1) {

        } else if (sequence == 2) {

        } else if (sequence == 3) {

        } else {
            throw new UnsupportedOperationException("the sequence " + sequence + " is not supported");
        }
    }

    private Method getMethod(Class<?> implClass, long methodHash) {
        for (Method method : implClass.getMethods()) {
            if (Util.getMethodHash(method) == methodHash) {
                return method;
            }
        }
        throw new IllegalArgumentException("No corresponding method found for hash: " + methodHash);
    }

    private Object[] unmarshalArguments(Method method, ObjectInputStream in) throws IOException, ClassNotFoundException {
        Class[] types = method.getParameterTypes();
        Object[] args = new Object[types.length];
        for (int i = 0; i < types.length; i++) {
            args[i] = Util.unmarshalValue(types[i], in);
        }
        return args;
    }

}
