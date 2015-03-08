package com.swayam.demo.rmi.server.core;

import java.rmi.Remote;
import java.rmi.server.ExportException;
import java.util.Collection;

import net.jini.jeri.BasicILFactory;
import net.jini.jeri.InvocationDispatcher;
import net.jini.jeri.ServerCapabilities;

public class BasicILFactoryWithLogging extends BasicILFactory {

    @Override
    protected InvocationDispatcher createInvocationDispatcher(Collection methods, Remote impl, ServerCapabilities caps)
            throws ExportException {
        if (impl == null) {
            throw new NullPointerException("impl is null");
        }
        return new BasicInvocationDispatcherWithLogging(methods, caps,
                null,
                null,
                getClassLoader());
    }

}
