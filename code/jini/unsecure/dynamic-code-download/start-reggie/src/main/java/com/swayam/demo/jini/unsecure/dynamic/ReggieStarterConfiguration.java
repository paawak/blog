package com.swayam.demo.jini.unsecure.dynamic;

import net.jini.config.AbstractConfiguration;
import net.jini.config.ConfigurationException;
import net.jini.security.BasicProxyPreparer;
import net.jini.security.ProxyPreparer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jini.start.NonActivatableServiceDescriptor;
import com.sun.jini.start.ServiceDescriptor;

public class ReggieStarterConfiguration extends AbstractConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReggieStarterConfiguration.class);

    private static final String COMPONENT_NAME = "com.sun.jini.start";

    @Override
    protected Object getEntryInternal(String component, String name, @SuppressWarnings("rawtypes") Class type,
            Object data) throws ConfigurationException {

        LOGGER.info("component:{}, name:{}, type:{}, data:{}", new Object[] { component, name, type, data });

        checkConfiguration(component);

        if (type == ServiceDescriptor[].class) {
            return new ServiceDescriptor[] { getServiceDescriptor() };
        } else if (type == ProxyPreparer.class) {
            return new BasicProxyPreparer();
        }

        return null;
    }

    private ServiceDescriptor getServiceDescriptor() throws ConfigurationException {
        return new NonActivatableServiceDescriptor(
                "",// codebase
                "", // policy
                "",// classpath
                "com.sun.jini.reggie.TransientRegistrarImpl",
                new ConfigurationFileWithLogging(
                        new String[] { "/kaaj/blog/code/jini/unsecure/jini-services/config/jeri-reggie.config" }),
                null,
                null);
    }

    private void checkConfiguration(String component) {
        if (!COMPONENT_NAME.equals(component)) {
            throw new IllegalArgumentException("The configuration from " + component + " is not supported");
        }
    }

}
