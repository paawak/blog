package com.swayam.demo.jini.unsecure.streaming.server.core.reggie;

import java.net.URL;
import java.util.Optional;

import net.jini.config.ConfigurationException;

import com.sun.jini.start.NonActivatableServiceDescriptor;
import com.sun.jini.start.ServiceDescriptor;

public class ReggieStarterConfiguration extends SimpleAbstractConfiguration {

    private static final String COMPONENT_NAME = "com.sun.jini.start";

    private final URL exporterConfigFileUrl;

    public ReggieStarterConfiguration(URL exporterConfigFileUrl) {
        this.exporterConfigFileUrl = exporterConfigFileUrl;
    }

    @Override
    Optional<Object> getEntry(String name, Class type) throws ConfigurationException {
        if (type == ServiceDescriptor[].class) {
            return Optional.of(new ServiceDescriptor[] { getServiceDescriptor() });
        }
        return Optional.empty();
    }

    @Override
    String getTargetedComponentName() {
        return COMPONENT_NAME;
    }

    private ServiceDescriptor getServiceDescriptor() throws ConfigurationException {
        return new NonActivatableServiceDescriptor(
                "",// codebase
                "", // policy
                "",// classpath
                "com.sun.jini.reggie.TransientRegistrarImpl",
                new ConfigurationFileWithLogging(exporterConfigFileUrl),
                // new ReggieConfiguration(),
                null,
                null);
    }

}
