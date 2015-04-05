package com.swayam.demo.rmi.shared.jini;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;

import net.jini.core.constraint.InvocationConstraints;
import net.jini.io.UnsupportedConstraintException;
import net.jini.jeri.InboundRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServletInboundRequest implements InboundRequest {

    private static final Logger LOG = LoggerFactory.getLogger(ServletInboundRequest.class);

    private final String httpServerUrl;

    public ServletInboundRequest(String httpServerUrl) {
        this.httpServerUrl = httpServerUrl;
    }

    @Override
    public void checkPermissions() {
        LOG.debug("AAAAAAAAAAAAAAAAAAAAAA");
    }

    @Override
    public InvocationConstraints checkConstraints(InvocationConstraints constraints) throws UnsupportedConstraintException {
        LOG.debug("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
        return null;
    }

    @Override
    public void populateContext(Collection context) {
        LOG.debug("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
    }

    @Override
    public InputStream getRequestInputStream() {
        LOG.debug("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
        try {
            return getUrlConnection().getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public OutputStream getResponseOutputStream() {
        LOG.debug("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
        try {
            return getUrlConnection().getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void abort() {
        LOG.debug("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
    }

    private URLConnection getUrlConnection() {

        LOG.info("trying to connect to {}", httpServerUrl);

        URL httpUrl;
        try {
            httpUrl = new URL(httpServerUrl + "?count=-1");
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
