package com.swayam.demo.rmi.server.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sun.jini.start.ServiceStarter;
import com.swayam.demo.rmi.server.core.reggie.ReggieStarterConfiguration;

public class SpringNonSecureRmiServer {

    private static final Logger LOG = LoggerFactory.getLogger(SpringNonSecureRmiServer.class);

    public static void main(String[] args) throws InterruptedException {

        String policyFilePath = SpringNonSecureRmiServer.class.getResource("/policy.all").getFile();

        LOG.info("Starting with the policy file {}", policyFilePath);

        System.setProperty("java.security.policy", policyFilePath);
        ServiceStarter.main(new ReggieStarterConfiguration(SpringNonSecureRmiServer.class
                .getResource("/jeri-reggie.config")));

        LOG.info("Started Reggie successfully");

        Thread.sleep(5_000);

        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("server-application.xml")) {
            LOG.info("The RMIServer is ready");
        }
    }

}
