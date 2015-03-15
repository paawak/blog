package com.swayam.demo.rmi.api.shared;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import net.jini.core.constraint.MethodConstraints;
import net.jini.jeri.BasicInvocationHandler;
import net.jini.jeri.ObjectEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicInvocationHandlerWithLogging implements InvocationHandler, Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(BasicInvocationHandlerWithLogging.class);

    private final ObjectEndpoint oe;
    private final MethodConstraints serverConstraints;

    public BasicInvocationHandlerWithLogging(ObjectEndpoint oe, MethodConstraints serverConstraints) {
        this.oe = oe;
        this.serverConstraints = serverConstraints;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        LOGGER.info("************** ");
        return new BasicInvocationHandler(oe, serverConstraints).invoke(proxy, method, args);
    }

}
