package com.swayam.demo.rmi.shared.jini.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

import net.jini.core.constraint.InvocationConstraints;
import net.jini.jeri.OutboundRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swayam.demo.rmi.shared.jini.IOStreamProvider;

public class ServletOutboundRequest implements OutboundRequest {

    private static final Logger LOG = LoggerFactory.getLogger(ServletOutboundRequest.class);

    private final IOStreamProvider ioStreamProvider;

    public ServletOutboundRequest(String host, int port) {
        ioStreamProvider = new ServletIOStreamProvider("http://" + host + ":" + port + "/write");
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
        LOG.debug("trying to return OutputStream...");
        try {
            return ioStreamProvider.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InputStream getResponseInputStream() {
        LOG.debug("trying to return InputStream...");
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
