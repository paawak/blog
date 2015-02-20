package com.swayam.demo.jini.unsecure.dynamic;

import net.jini.config.Configuration;
import net.jini.config.ConfigurationException;
import net.jini.config.ConfigurationProvider;

import com.sun.jini.start.ServiceStarter;

public class ReggieStarter {
    public static void main(String[] args) throws ConfigurationException {
        Configuration config = ConfigurationProvider
                .getInstance(new String[] {
                        "/kaaj/blog/code/jini/unsecure/jini-services/config/start-reggie.config" });
        ServiceStarter.main(config);
    }
}
