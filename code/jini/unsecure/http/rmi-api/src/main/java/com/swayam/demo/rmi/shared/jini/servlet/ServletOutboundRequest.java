package com.swayam.demo.rmi.shared.jini.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.jini.core.constraint.InvocationConstraints;
import net.jini.jeri.OutboundRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swayam.demo.rmi.shared.jini.IOStreamProvider;

public class ServletOutboundRequest implements OutboundRequest {

    private static final Logger LOG = LoggerFactory.getLogger(ServletOutboundRequest.class);

    public static final String OUTBOUND_CALL_URI = "/write/";

    private final String baseUrl;

    private final Map<Integer, IOStreamProvider> providerMap;

    private int outputCounter = 0;
    private int inputCounter = 0;

    public ServletOutboundRequest(String host, int port) {
        baseUrl = "http://" + host + ":" + port + OUTBOUND_CALL_URI;
        providerMap = new HashMap<>(3);
    }

    @Override
    public void populateContext(@SuppressWarnings("rawtypes") Collection context) {
        LOG.debug("populating context...");
    }

    @Override
    public InvocationConstraints getUnfulfilledConstraints() {
        LOG.debug("returning UnfulfilledConstraints...");
        return InvocationConstraints.EMPTY;
    }

    @Override
    public OutputStream getRequestOutputStream() {
        outputCounter++;
        String url = baseUrl + outputCounter;
        LOG.debug("trying to return OutputStream for the url: {}", url);
        IOStreamProvider ioStreamProvider = new ServletIOStreamProvider(url);
        providerMap.put(outputCounter, ioStreamProvider);
        try {
            return ioStreamProvider.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InputStream getResponseInputStream() {
        inputCounter++;
        String url = baseUrl + inputCounter;
        LOG.debug("trying to return InputStream for the url: {}", url);
        IOStreamProvider ioStreamProvider = providerMap.get(inputCounter);
        try {
            return ioStreamProvider.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean getDeliveryStatus() {
        LOG.debug("getting delivery status...");
        return false;
    }

    @Override
    public void abort() {
        LOG.debug("aborting...");
    }

}
