package com.swayam.demo.rmi.shared.jini.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.rmi.Remote;
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

    public ServletBasedInvocationHandler(String httpUrl, ObjectEndpoint oe, MethodConstraints serverConstraints) {
        super(oe, serverConstraints);
        this.httpUrl = httpUrl;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        LOGGER.info("************** invoking proxy");
        return invokeRemoteMethod(proxy, method, args);
        // return super.invoke(proxy, method, args);
    }

    private Object invokeRemoteMethod(Object proxy, Method method, Object[] args) throws IOException, ClassNotFoundException {
        Util.checkProxyRemoteMethod(proxy.getClass(), method);

        Class<?> proxyClass = proxy.getClass();
        String implClassName = getRemoteImplClassName(proxyClass).getName();
        Collection context = Collections.emptyList();

        IOStreamProvider ioStreamProvider = new ServletIOStreamProvider(httpUrl);

        // first write the details of the remote method
        try (MarshalOutputStream out = new MarshalOutputStream(ioStreamProvider.getOutputStream(), context);) {
            out.writeObject(implClassName);
            marshalMethod(proxy, method, out, context);
            args = (args == null) ? new Object[0] : args;
            marshalArguments(proxy, method, args, out, context);
        }

        // then read the results of the remote invocation
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        try (MarshalInputStream mis = new MarshalInputStream(ioStreamProvider.getInputStream(), cl, false, cl, context);) {
            return mis.readObject();
        }
    }

    private Class<?> getRemoteImplClassName(Class<?> proxyClass) {
        for (Class<?> parentInterface : proxyClass.getInterfaces()) {
            LOGGER.info("parentInterface: {}", parentInterface);
            for (Class<?> grandParentInterface : parentInterface.getInterfaces()) {
                if (grandParentInterface == Remote.class) {
                    return parentInterface;
                }
            }
        }

        throw new IllegalArgumentException("No class found with " + Remote.class.getName() + " implementation");
    }

}
