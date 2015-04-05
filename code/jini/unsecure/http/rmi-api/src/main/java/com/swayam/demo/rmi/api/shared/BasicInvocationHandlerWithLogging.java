package com.swayam.demo.rmi.api.shared;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;

import net.jini.core.constraint.MethodConstraints;
import net.jini.io.MarshalOutputStream;
import net.jini.jeri.BasicInvocationHandler;
import net.jini.jeri.ObjectEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicInvocationHandlerWithLogging extends BasicInvocationHandler {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(BasicInvocationHandlerWithLogging.class);

    public BasicInvocationHandlerWithLogging(ObjectEndpoint oe, MethodConstraints serverConstraints) {
        super(oe, serverConstraints);
    }

    public BasicInvocationHandlerWithLogging(BasicInvocationHandlerWithLogging basicInvocationHandlerWithLogging, MethodConstraints serverConstraints) {
        super(basicInvocationHandlerWithLogging, serverConstraints);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        LOGGER.info("************** ");
        writeToServer(proxy, method, args);
        return super.invoke(proxy, method, args);
    }

    private void writeToServer(Object proxy, Method method, Object[] args) throws IOException {
        URLConnection urlConnection = getUrlConnection();
        try (MarshalOutputStream os = new MarshalOutputStream(urlConnection.getOutputStream(), Collections.emptyList());) {
            os.writeObject(proxy.getClass().getName());
            os.writeObject(method.getName());
            os.writeObject(args);
            os.flush();
            os.close();
        }
        // this is vety important, the request will not hit the server if this
        // is not done
        try (InputStream is = urlConnection.getInputStream();) {
            // do nothing
        }
    }

    private URLConnection getUrlConnection() {

        String url = "http://localhost:8100/?count=23";

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
