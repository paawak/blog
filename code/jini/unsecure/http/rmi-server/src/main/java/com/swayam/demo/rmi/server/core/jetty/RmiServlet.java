package com.swayam.demo.rmi.server.core.jetty;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.jini.id.Uuid;
import net.jini.id.UuidFactory;
import net.jini.io.MarshalInputStream;
import net.jini.io.MarshalOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.swayam.demo.rmi.server.core.rmi.RMIServerStarter;
import com.swayam.demo.rmi.shared.api.dto.BankDetail;
import com.swayam.demo.rmi.shared.api.dto.BankDetailGroups;
import com.swayam.demo.rmi.shared.api.service.BankDetailService;
import com.swayam.demo.rmi.shared.jini.servlet.ServletInboundRequest;
import com.swayam.demo.rmi.shared.jini.servlet.ServletOutboundRequest;

public class RmiServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger(RmiServlet.class);

    private Uuid uuid;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOG.info("processing GET");
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOG.info("processing POST");
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String requestUri = request.getRequestURI();

        LOG.info("processing request for requestUri: `{}`", requestUri);

        if (requestUri.startsWith(ServletInboundRequest.INBOUND_CALL_URI)) {
            int sequence = Integer.parseInt(requestUri.substring(ServletInboundRequest.INBOUND_CALL_URI.length()));
            handleInboundRequest(request, response, sequence);
        } else if (requestUri.startsWith(ServletOutboundRequest.OUTBOUND_CALL_URI)) {
            int sequence = Integer.parseInt(requestUri.substring(ServletOutboundRequest.OUTBOUND_CALL_URI.length()));
            handleOutboundRequest(request, response, sequence);
        } else {
            throw new UnsupportedOperationException("The requestUri: " + requestUri + " is not supported");
        }

    }

    private void handleInboundRequest(HttpServletRequest request, HttpServletResponse response, int sequence) throws IOException {
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

    private void handleOutboundRequest(HttpServletRequest request, HttpServletResponse response, int sequence) throws IOException {
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

            ApplicationContext context = RMIServerStarter.getApplicationContext();

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
