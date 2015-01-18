package com.swayam.demo.rmi.client;

import java.net.MalformedURLException;
import java.rmi.server.RMIClassLoaderSpi;

import net.jini.loader.pref.PreferredClassProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyClassProvider extends RMIClassLoaderSpi {

    private static final Logger LOG = LoggerFactory.getLogger(MyClassProvider.class);

    private final PreferredClassProvider preferredClassProvider;

    public MyClassProvider() {
        // super(true);
        this.preferredClassProvider = new PreferredClassProvider();
        LOG.debug("init");
    }

    @Override
    public Class<?> loadClass(String codebase, String name, ClassLoader defaultLoader) throws MalformedURLException, ClassNotFoundException {
        LOG.debug("codebase: {}, name: {}", codebase, name);
        return preferredClassProvider.loadClass(codebase, name, defaultLoader);
    }

    @Override
    public Class<?> loadProxyClass(String codebase, String[] interfaces, ClassLoader defaultLoader) throws MalformedURLException, ClassNotFoundException {
        LOG.debug("codebase: {}, interfaces: {}", codebase, interfaces);
        return preferredClassProvider.loadProxyClass(codebase, interfaces, defaultLoader);
    }

    @Override
    public ClassLoader getClassLoader(String codebase) throws MalformedURLException {
        LOG.debug("codebase: {}", codebase);
        return preferredClassProvider.getClassLoader(codebase);
    }

    @Override
    public String getClassAnnotation(Class<?> cl) {
        LOG.debug("cl: {}", cl);
        return preferredClassProvider.getClassAnnotation(cl);
    }

}
