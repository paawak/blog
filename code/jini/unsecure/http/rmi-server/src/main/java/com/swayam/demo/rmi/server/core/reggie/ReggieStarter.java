package com.swayam.demo.rmi.server.core.reggie;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jini.start.ServiceStarter;
import com.swayam.demo.rmi.server.core.SpringNonSecureRmiServer;

public class ReggieStarter implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(ReggieStarter.class);

    private final CountDownLatch signalToStartReggie;
    private final CountDownLatch signalToStartRmiServer;

    public ReggieStarter(CountDownLatch signalToStartReggie, CountDownLatch signalToStartRmiServer) {
        this.signalToStartReggie = signalToStartReggie;
        this.signalToStartRmiServer = signalToStartRmiServer;
    }

    @Override
    public void run() {

        try {
            signalToStartReggie.await(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            LOG.error("Reggie was interrupted while waiting for Jetty to start", e);
            throw new RuntimeException(e);
        }

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