package com.swayam.demo.jini.unsecure.streaming.server.core.reggie;

import java.util.Optional;

import net.jini.config.ConfigurationException;
import net.jini.config.NoSuchEntryException;
import net.jini.discovery.DiscoveryManagement;
import net.jini.export.Exporter;
import net.jini.jeri.BasicILFactory;
import net.jini.jeri.BasicJeriExporter;
import net.jini.jeri.tcp.TcpServerEndpoint;

public class ReggieConfiguration extends SimpleAbstractConfiguration {

    private static final String COMPONENT_NAME = "com.sun.jini.reggie";

    @Override
    Optional<Object> getEntry(String name, Class type) throws ConfigurationException {
        if (type == Exporter.class) {
            return Optional.of(getExporter());
        } else if (name.equals("initialUnicastDiscoveryPort")) {
            return Optional.of(4160);
        } else if (type == DiscoveryManagement.class) {
            throw new NoSuchEntryException("No entry found");
            // try {
            // return Optional.of(new LookupDiscoveryManager(
            // DiscoveryGroupManagement.NO_GROUPS, null, null));
            // } catch (IOException e) {
            // throw new ConfigurationException("failed to initialize a " +
            // LookupDiscoveryManager.class, e);
            // }
        }
        // else if (name.equals("multicastRequestSubjectChecker")) {
        // return Optional.of(new ClientSubjectChecker() {
        // @Override
        // public void checkClientSubject(Subject subject) {
        // // TODO Auto-generated method stub
        //
        // }
        // });
        // }
        return Optional.empty();
    }

    @Override
    String getTargetedComponentName() {
        return COMPONENT_NAME;
    }

    private Exporter getExporter() {
        return new BasicJeriExporter(TcpServerEndpoint.getInstance(0), new BasicILFactory());
    }

}
