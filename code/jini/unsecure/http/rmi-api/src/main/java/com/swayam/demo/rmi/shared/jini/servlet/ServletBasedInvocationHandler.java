package com.swayam.demo.rmi.shared.jini.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;

import net.jini.core.constraint.MethodConstraints;
import net.jini.io.MarshalInputStream;
import net.jini.io.MarshalOutputStream;
import net.jini.jeri.BasicInvocationHandler;
import net.jini.jeri.ObjectEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jini.jeri.internal.runtime.Util;
import com.swayam.demo.rmi.shared.jini.IOStreamProvider;

public class ServletBasedInvocationHandler extends BasicInvocationHandler {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(ServletBasedInvocationHandler.class);

    private final String httpUrl;
    private final String implClassName;

    public ServletBasedInvocationHandler(String httpUrl, ObjectEndpoint oe, MethodConstraints serverConstraints, String implClassName) {
        super(oe, serverConstraints);
        this.httpUrl = httpUrl;
        this.implClassName = implClassName;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        LOGGER.info("************** invoking proxy {}", proxy.getClass());
        return invokeRemoteMethod(proxy, method, args);
        // return super.invoke(proxy, method, args);
    }

    private Object invokeRemoteMethod(Object proxy, Method method, Object[] args) throws IOException, ClassNotFoundException {
        Util.checkProxyRemoteMethod(proxy.getClass(), method);

        Collection context = Collections.emptyList();

        IOStreamProvider ioStreamProvider = new ServletIOStreamProvider(httpUrl);

        try (MarshalOutputStream out = new MarshalOutputStream(ioStreamProvider.getOutputStream(), context);) {
            out.writeObject(implClassName);
            marshalMethod(proxy, method, out, context);
            args = (args == null) ? new Object[0] : args;
            marshalArguments(proxy, method, args, out, context);
        }

        // this is very important, the request will not hit the server if this
        // is not done
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        try (MarshalInputStream mis = new MarshalInputStream(ioStreamProvider.getInputStream(), cl, false, cl, context);) {
            return mis.readObject();
        }
    }

}
