package com.swayam.demo.rmi.server.core.rmi;

import java.lang.reflect.InvocationHandler;
import java.rmi.Remote;
import java.rmi.server.ExportException;
import java.util.Collection;

import net.jini.jeri.BasicILFactory;
import net.jini.jeri.InvocationDispatcher;
import net.jini.jeri.ObjectEndpoint;
import net.jini.jeri.ServerCapabilities;

import com.swayam.demo.rmi.api.shared.BasicInvocationHandlerWithLogging;

public class BasicILFactoryWithLogging extends BasicILFactory {

    @Override
    protected InvocationDispatcher createInvocationDispatcher(Collection methods, Remote impl, ServerCapabilities caps) throws ExportException {
        if (impl == null) {
            throw new NullPointerException("impl is null");
        }
        return new BasicInvocationDispatcherWithLogging(methods, caps, null, null, getClassLoader());
    }

    @Override
    protected InvocationHandler createInvocationHandler(Class[] interfaces, Remote impl, ObjectEndpoint oe) throws ExportException {
        for (int i = interfaces.length; --i >= 0;) {
            if (interfaces[i] == null) {
                throw new NullPointerException();
            }
        }
        if (impl == null) {
            throw new NullPointerException();
        }
        return new BasicInvocationHandlerWithLogging(oe, null);
    }

}
