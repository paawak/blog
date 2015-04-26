package com.swayam.demo.rmi.server.core.jini.servlet;

import java.lang.reflect.InvocationHandler;
import java.rmi.Remote;
import java.rmi.server.ExportException;
import java.util.Collection;

import net.jini.jeri.BasicILFactory;
import net.jini.jeri.InvocationDispatcher;
import net.jini.jeri.ObjectEndpoint;
import net.jini.jeri.ServerCapabilities;

import com.swayam.demo.rmi.server.core.jini.BasicInvocationDispatcherWithLogging;
import com.swayam.demo.rmi.shared.jini.servlet.ServletBasedInvocationHandler;

public class ServletBasedILFactory extends BasicILFactory {

    private final String httpUrl;

    public ServletBasedILFactory(String httpUrl) {
        this.httpUrl = httpUrl;
    }

    @Override
    protected InvocationDispatcher createInvocationDispatcher(Collection methods, Remote impl, ServerCapabilities caps) throws ExportException {
        return new BasicInvocationDispatcherWithLogging(methods, caps, null, null, getClassLoader());
    }

    @Override
    protected InvocationHandler createInvocationHandler(Class[] interfaces, Remote impl, ObjectEndpoint oe) throws ExportException {
        return new ServletBasedInvocationHandler(httpUrl, oe, null);
    }

    @Override
    public Instances createInstances(Remote impl, ObjectEndpoint oe, ServerCapabilities caps) throws ExportException {
        return super.createInstances(impl, oe, caps);
    }

}
