package com.swayam.demo.rmi.server.core.rmi;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RMIServerStarter implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(RMIServerStarter.class);

    private final CountDownLatch signalToStartRmiServer;

    public RMIServerStarter(CountDownLatch signalToStartRmiServer) {
        this.signalToStartRmiServer = signalToStartRmiServer;
    }

    @Override
    public void run() {

        try {
            signalToStartRmiServer.await(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            LOG.error("RMI Server was interrupted while waiting for Reggie to start", e);
            throw new RuntimeException(e);
        }

        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("server-application.xml")) {
            LOG.info("**************************************The RMIServer is ready");
        }
    }

}