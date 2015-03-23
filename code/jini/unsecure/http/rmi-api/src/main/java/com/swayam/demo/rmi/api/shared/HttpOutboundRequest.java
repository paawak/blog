package com.swayam.demo.rmi.api.shared;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;

import net.jini.core.constraint.InvocationConstraints;
import net.jini.jeri.OutboundRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpOutboundRequest implements OutboundRequest {

    private static final Logger LOG = LoggerFactory.getLogger(HttpOutboundRequest.class);

    private final String host;
    private final int port;

    public HttpOutboundRequest(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void populateContext(Collection context) {
        LOG.debug("11111111111111111111111111");
    }

    @Override
    public InvocationConstraints getUnfulfilledConstraints() {
        LOG.debug("22222222222222222222222222222");
        return null;
    }

    @Override
    public OutputStream getRequestOutputStream() {
        LOG.debug("333333333333333333333333333333333");

        String url = "http://" + host + ":" + port + "/";

        LOG.info("trying to connect to {}", url);

        URL httpUrl;
        try {
            httpUrl = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        URLConnection urlConnection;
        try {
            urlConnection = httpUrl.openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        urlConnection.setDoOutput(true);

        try {
            return urlConnection.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InputStream getResponseInputStream() {
        LOG.debug("44444444444444444444444444444444444444");
        return null;
    }

    @Override
    public boolean getDeliveryStatus() {
        LOG.debug("55555555555555555555555555555555555555555555555555");
        return false;
    }

    @Override
    public void abort() {
        LOG.debug("666666666666666666666666666666");
    }

}
