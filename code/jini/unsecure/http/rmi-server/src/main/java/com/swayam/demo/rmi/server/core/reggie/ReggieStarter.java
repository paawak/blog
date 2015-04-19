package com.swayam.demo.rmi.server.core.reggie;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jini.start.ServiceStarter;
import com.swayam.demo.rmi.server.core.SpringNonSecureRmiServer;

public class ReggieStarter implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(ReggieStarter.class);

    private final CountDownLatch signalToStartRmiServer;

    public ReggieStarter(CountDownLatch signalToStartRmiServer) {
        this.signalToStartRmiServer = signalToStartRmiServer;
    }

    @Override
    public void run() {

        String policyFilePath = SpringNonSecureRmiServer.class.getResource("/policy.all").getFile();

        LOG.info("Starting with the policy file {}", policyFilePath);

        System.setProperty("java.security.policy", policyFilePath);

        ServiceStarter.main(new ReggieStarterConfiguration(SpringNonSecureRmiServer.class.getResource("/jeri-reggie.config")));

        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            LOG.error("Reggie was interrupted while starting up", e);
            throw new RuntimeException(e);
        }

        LOG.info("**************************************Started Reggie successfully");

        signalToStartRmiServer.countDown();

    }

}