package com.swayam.demo.rmi.shared.jini.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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

    private final Map<Integer, IOStreamProvider> providerMap;

    private int outputSequence = 0;
    private int inputSequence = 0;

    public ServletInboundRequest(String serverUrl) {
        baseUrl = serverUrl + INBOUND_CALL_URI;
        providerMap = new HashMap<>(3);
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
    public OutputStream getResponseOutputStream() {
        outputSequence++;
        String url = baseUrl + outputSequence;
        LOG.debug("trying to return OutputStream for the url: {}", url);
        IOStreamProvider ioStreamProvider = new ServletIOStreamProvider(url);
        try {
            return ioStreamProvider.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InputStream getRequestInputStream() {
        inputSequence++;
        String url = baseUrl + inputSequence;
        LOG.debug("trying to return InputStream for the url: {}", url);
        IOStreamProvider ioStreamProvider = new ServletIOStreamProvider(url);
        providerMap.put(inputSequence, ioStreamProvider);
        try {
            return ioStreamProvider.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void abort() {
        LOG.debug("abort");
    }

}
