package com.swayam.demo.jini.unsecure.streaming.server.core;

import java.lang.reflect.Method;
import java.rmi.Remote;
import java.rmi.server.ExportException;
import java.util.Collection;

import net.jini.core.constraint.MethodConstraints;
import net.jini.jeri.BasicInvocationDispatcher;
import net.jini.jeri.ServerCapabilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jini.jeri.internal.runtime.Util;

public class BasicInvocationDispatcherWithLogging extends BasicInvocationDispatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasicInvocationDispatcherWithLogging.class);

    public BasicInvocationDispatcherWithLogging(Collection methods, ServerCapabilities serverCapabilities,
            MethodConstraints serverConstraints, Class permissionClass, ClassLoader loader) throws ExportException {
        super(methods, serverCapabilities, serverConstraints, permissionClass, loader);
    }

    @Override
    protected Object invoke(Remote impl, Method method, Object[] args, Collection context) throws Throwable {
        LOGGER.info("************** service: {}, client: {}, context: {}", new Object[] { impl.getClass(),
                Util.getClientHostString(), context });
        return super.invoke(impl, method, args, context);
    }

}
