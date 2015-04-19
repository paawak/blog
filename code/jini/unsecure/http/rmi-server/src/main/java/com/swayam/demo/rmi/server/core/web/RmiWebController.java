package com.swayam.demo.rmi.server.core.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.swayam.demo.rmi.shared.api.dto.BankDetail;
import com.swayam.demo.rmi.shared.api.dto.BankDetailGroups;
import com.swayam.demo.rmi.shared.api.service.BankDetailService;

@Controller
public class RmiWebController {

    private static final Logger LOG = LoggerFactory.getLogger(RmiWebController.class);

    private Uuid uuid;

    // @RequestMapping(value = { "/INBOUND_CALL/*", "/OUTBOUND_CALL/*" })
    // public void processRequest(HttpServletRequest request,
    // HttpServletResponse response) throws IOException {
    //
    // String requestUri = request.getRequestURI();
    //
    // LOG.info("processing request for requestUri: `{}`", requestUri);
    //
    // if (requestUri.startsWith(ServletInboundRequest.INBOUND_CALL_URI)) {
    // int sequence =
    // Integer.parseInt(requestUri.substring(ServletInboundRequest.INBOUND_CALL_URI.length()));
    // handleInboundRequest(request, response, sequence);
    // } else if
    // (requestUri.startsWith(ServletOutboundRequest.OUTBOUND_CALL_URI)) {
    // int sequence =
    // Integer.parseInt(requestUri.substring(ServletOutboundRequest.OUTBOUND_CALL_URI.length()));
    // handleOutboundRequest(request, response, sequence);
    // } else {
    // throw new UnsupportedOperationException("The requestUri: " + requestUri +
    // " is not supported");
    // }
    //
    // }

    @RequestMapping(value = "/INBOUND_CALL/{sequence}")
    public void handleInboundRequest(HttpServletRequest request, HttpServletResponse response, @PathVariable int sequence) throws IOException {
        // ClassLoader cl = Thread.currentThread().getContextClassLoader();
        // try (MarshalInputStream mis = new
        // MarshalInputStream(request.getInputStream(), cl, false, cl,
        // Collections.emptyList());) {
        // String className = (String) mis.readObject();
        // System.out.println("******* className: " + className);
        // String method = (String) mis.readObject();
        // System.out.println("******* method: " + method);
        // Object[] args = (Object[]) mis.readObject();
        // System.out.println("******* args: " + args);
        // }
        writeToInboundRequest(response, sequence);
    }

    @RequestMapping(value = "/OUTBOUND_CALL/{sequence}")
    public void handleOutboundRequest(HttpServletRequest request, HttpServletResponse response, @PathVariable int sequence) throws IOException {
        readFromOutboundRequest(request, sequence);
        writeToOutboundRequest(response, sequence);
    }

    private void writeToOutboundRequest(HttpServletResponse response, int sequence) throws IOException {
        LOG.info("writing output for the sequence: `{}`", sequence);

        if ((sequence == 1) || (sequence == 2)) {

            OutputStream os = response.getOutputStream();
            os.write(0x01);
            os.flush();
            os.close();

        } else if (sequence == 3) {

            ApplicationContext context = RmiServletContextAttributeListener.getApplicationContext();

            BankDetailService bankDetailService = context.getBean("bankDetailServiceImpl", BankDetailService.class);
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
            uuid = UuidFactory.read(is);
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
            // write uuid
            if (uuid != null) {
                LOG.info("write uuid: {}", uuid);
                OutputStream os = response.getOutputStream();
                uuid.write(os);
                os.flush();
                os.close();
            }

        } else if (sequence == 2) {

        } else if (sequence == 3) {

        } else {
            throw new UnsupportedOperationException("the sequence " + sequence + " is not supported");
        }
    }

}
