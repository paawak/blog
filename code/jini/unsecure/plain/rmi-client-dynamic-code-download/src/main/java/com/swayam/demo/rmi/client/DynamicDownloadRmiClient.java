package com.swayam.demo.rmi.client;

import java.lang.reflect.Method;
import java.rmi.Remote;
import java.rmi.server.RMIClassLoaderSpi;

import net.jini.core.discovery.LookupLocator;
import net.jini.core.entry.Entry;
import net.jini.core.lookup.ServiceRegistrar;
import net.jini.core.lookup.ServiceTemplate;
import net.jini.lookup.entry.Name;

public class DynamicDownloadRmiClient {

    /**
     * Needs to be run with the following JVM args:<br/>
     * <ul>
     * <li>
     * -Djava.security.policy=/full-path/RmiClient/src/main/resources/policy.all
     * </li>
     * <li>-Djava.rmi.server.RMIClassLoaderSpi=net.jini.loader.pref.
     * PreferredClassProvider</li>
     * <li>-Djava.rmi.server.codebase=http://localhost:8080/RmiService-1.0.jar</li>
     * </ul>
     */
    public static void main(String[] args) throws Exception {

        System.setProperty("java.security.policy", DynamicDownloadRmiClient.class.getResource("/policy.all").getPath());

        // the below line is put only for debugging purposes, its not needed, as
        // the default class loader is good enough
        System.setProperty(RMIClassLoaderSpi.class.getName(), MyClassProvider.class.getName());

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
            System.out.println("DynamicDownloadRmiClient.main() setting custom security manager");
        }

        LookupLocator lookup = new LookupLocator("jini://localhost:4160");

        ServiceRegistrar registrar = lookup.getRegistrar();

        // give the poor guys some time to lookup
        Thread.sleep(1000);

        Remote userService = (Remote) registrar.lookup(new ServiceTemplate(null, null, new Entry[] { new Name("UserService") }));

        Method getCurrentUsers = userService.getClass().getDeclaredMethod("getCurrentUsers");
        Object result = getCurrentUsers.invoke(userService);
        System.out.println("Getting current users from remote: " + result);

    }

}
