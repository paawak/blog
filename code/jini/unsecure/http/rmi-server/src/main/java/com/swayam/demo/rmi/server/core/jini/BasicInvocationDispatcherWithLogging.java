package com.swayam.demo.rmi.server.core.jini;

import java.rmi.Remote;
import java.rmi.server.ExportException;
import java.util.Collection;

import net.jini.core.constraint.MethodConstraints;
import net.jini.jeri.BasicInvocationDispatcher;
import net.jini.jeri.InboundRequest;
import net.jini.jeri.InvocationDispatcher;
import net.jini.jeri.ServerCapabilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicInvocationDispatcherWithLogging implements InvocationDispatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasicInvocationDispatcherWithLogging.class);

    private final InvocationDispatcher delegatingInvocationDispatcher;

    public BasicInvocationDispatcherWithLogging(Collection methods, ServerCapabilities serverCapabilities, MethodConstraints serverConstraints, Class permissionClass,
            ClassLoader loader) throws ExportException {
        delegatingInvocationDispatcher = new BasicInvocationDispatcher(methods, serverCapabilities, serverConstraints, permissionClass, loader);
    }

    @Override
    public void dispatch(Remote impl, InboundRequest request, Collection context) {
        LOGGER.info("************** service: {}, request: {}, context: {}", new Object[] { impl.getClass(), request, context });
        delegatingInvocationDispatcher.dispatch(impl, request, context);
    }

}
