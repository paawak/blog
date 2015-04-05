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

public class ServletOutboundRequest implements OutboundRequest {

    private static final Logger LOG = LoggerFactory.getLogger(ServletOutboundRequest.class);

    private final String host;
    private final int port;

    private int outputCounter = 0;
    private int inputCounter = 0;

    public ServletOutboundRequest(String host, int port) {
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
        return InvocationConstraints.EMPTY;
    }

    @Override
    public OutputStream getRequestOutputStream() {
        outputCounter++;
        LOG.debug("333333333333333333333333333333333 " + outputCounter);
        try {
            return getUrlConnection(outputCounter).getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InputStream getResponseInputStream() {
        inputCounter++;
        LOG.debug("44444444444444444444444444444444444444 " + inputCounter);
        try {
            return getUrlConnection(inputCounter).getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    private URLConnection getUrlConnection(int counter) {

        String url = "http://" + host + ":" + port + "/?count=" + counter;

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
        urlConnection.setDoInput(true);

        try {
            urlConnection.connect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return urlConnection;
    }

}
