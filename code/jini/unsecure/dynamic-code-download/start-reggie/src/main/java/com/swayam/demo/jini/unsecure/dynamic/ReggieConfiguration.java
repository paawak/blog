package com.swayam.demo.jini.unsecure.dynamic;

import net.jini.config.AbstractConfiguration;
import net.jini.config.Configuration;
import net.jini.config.ConfigurationException;
import net.jini.export.Exporter;
import net.jini.jeri.BasicILFactory;
import net.jini.jeri.BasicJeriExporter;
import net.jini.jeri.tcp.TcpServerEndpoint;
import net.jini.security.BasicProxyPreparer;
import net.jini.security.ProxyPreparer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReggieConfiguration extends AbstractConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReggieConfiguration.class);

    private static final String COMPONENT_NAME = "com.sun.jini.reggie";

    @Override
    protected Object getEntryInternal(String component, String name, @SuppressWarnings("rawtypes") Class type,
            Object data)
            throws ConfigurationException {

        LOGGER.info("component:{}, name:{}, type:{}, data:{}", new Object[] { component, name, type, data });

        checkConfiguration(component);

        if (type == Exporter.class) {
            return getExporter();
        } else if (name.equals("initialUnicastDiscoveryPort")) {
            return new Primitive(4160);
        } else if (name.equals("initialMemberGroups")) {
            return new String[0];
        } else if (type == ProxyPreparer.class) {
            return new BasicProxyPreparer();
        } else if (name.equals("minMaxEventLease")) {
            return new Primitive(31536000000000L);
        } else if (name.equals("minMaxServiceLease")) {
            return Configuration.NO_DEFAULT;
        }

        return null;
    }

    private Exporter getExporter() {
        return new BasicJeriExporter(TcpServerEndpoint.getInstance(0), new BasicILFactory());
    }

    private void checkConfiguration(String component) {
        if (!COMPONENT_NAME.equals(component)) {
            throw new IllegalArgumentException("The configuration from " + component + " is not supported");
        }
    }

}
