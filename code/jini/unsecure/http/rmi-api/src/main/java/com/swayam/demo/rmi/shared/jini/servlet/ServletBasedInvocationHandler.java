package com.swayam.demo.rmi.shared.jini.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Collections;

import net.jini.core.constraint.MethodConstraints;
import net.jini.io.MarshalOutputStream;
import net.jini.jeri.BasicInvocationHandler;
import net.jini.jeri.ObjectEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swayam.demo.rmi.shared.jini.IOStreamProvider;

public class ServletBasedInvocationHandler extends BasicInvocationHandler {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(ServletBasedInvocationHandler.class);

    private final String httpUrl;

    private final String implClassName;

    public ServletBasedInvocationHandler(String httpUrl, ObjectEndpoint oe, MethodConstraints serverConstraints, String implClassName) {
        super(oe, serverConstraints);
        this.implClassName = implClassName;
        this.httpUrl = httpUrl;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        LOGGER.info("************** ");
        writeToServer(proxy, method, args);
        return super.invoke(proxy, method, args);
    }

    private void writeToServer(Object proxy, Method method, Object[] args) throws IOException {
        IOStreamProvider ioStreamProvider = new ServletIOStreamProvider(httpUrl);
        try (MarshalOutputStream os = new MarshalOutputStream(ioStreamProvider.getOutputStream(), Collections.emptyList());) {
            os.writeObject(implClassName);
            os.writeObject(method.getName());
            os.writeObject(args);
            os.flush();
            os.close();
        }
        // this is very important, the request will not hit the server if this
        // is not done
        try (InputStream is = ioStreamProvider.getInputStream();) {
            // do nothing
        }
    }

}
