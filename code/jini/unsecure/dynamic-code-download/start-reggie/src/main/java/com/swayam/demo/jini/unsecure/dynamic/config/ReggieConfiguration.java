package com.swayam.demo.jini.unsecure.dynamic.config;

import java.util.Optional;

import net.jini.export.Exporter;
import net.jini.jeri.BasicILFactory;
import net.jini.jeri.BasicJeriExporter;
import net.jini.jeri.tcp.TcpServerEndpoint;

public class ReggieConfiguration extends SimpleAbstractConfiguration {

    private static final String COMPONENT_NAME = "com.sun.jini.reggie";

    @Override
    Optional<Object> getEntry(String name, Class type) {
        if (type == Exporter.class) {
            return Optional.of(getExporter());
        } else if (name.equals("initialUnicastDiscoveryPort")) {
            return Optional.of(4160);
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
