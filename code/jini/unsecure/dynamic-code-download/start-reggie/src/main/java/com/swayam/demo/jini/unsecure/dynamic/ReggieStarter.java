package com.swayam.demo.jini.unsecure.dynamic;

import net.jini.config.ConfigurationException;

import com.sun.jini.start.ServiceStarter;
import com.swayam.demo.jini.unsecure.dynamic.config.ReggieStarterConfiguration;

public class ReggieStarter {

    public static void main(String[] args) throws ConfigurationException {
        System.setProperty("java.security.policy", System.getProperty("user.home") + "/jini/policy.all");
        ServiceStarter.main(new ReggieStarterConfiguration());
    }

}
