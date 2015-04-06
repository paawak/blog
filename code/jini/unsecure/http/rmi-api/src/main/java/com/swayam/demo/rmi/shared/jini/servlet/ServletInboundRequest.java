package com.swayam.demo.rmi.shared.jini.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

import net.jini.core.constraint.InvocationConstraints;
import net.jini.io.UnsupportedConstraintException;
import net.jini.jeri.InboundRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swayam.demo.rmi.shared.jini.IOStreamProvider;

public class ServletInboundRequest implements InboundRequest {

    private static final Logger LOG = LoggerFactory.getLogger(ServletInboundRequest.class);

    public static final String INBOUND_CALL_URI = "/INBOUND_CALL/";

    private final String baseUrl;
    private final IOStreamProvider ioStreamProvider;

    public ServletInboundRequest(String host, int port) {
        baseUrl = "http://" + host + ":" + port + INBOUND_CALL_URI;
        ioStreamProvider = new ServletIOStreamProvider(baseUrl);
    }

    @Override
    public void checkPermissions() {
        LOG.debug("checking permissions");
    }

    @Override
    public InvocationConstraints checkConstraints(InvocationConstraints constraints) throws UnsupportedConstraintException {
        LOG.debug("checking constraints");
        return InvocationConstraints.EMPTY;
    }

    @Override
    public void populateContext(@SuppressWarnings("rawtypes") Collection context) {
        LOG.debug("populate context");
    }

    @Override
    public InputStream getRequestInputStream() {
        LOG.debug("trying to return InputStream");
        try {
            return ioStreamProvider.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public OutputStream getResponseOutputStream() {
        LOG.debug("trying to return OutputStream");
        try {
            return ioStreamProvider.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void abort() {
        LOG.debug("abort");
    }

}
