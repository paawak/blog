package com.swayam.demo.rmi.server.core;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.swayam.demo.rmi.server.core.http.JettyServerStarter;
import com.swayam.demo.rmi.server.core.reggie.ReggieStarter;

public class SpringNonSecureRmiServer {

    private static final Logger LOG = LoggerFactory.getLogger(SpringNonSecureRmiServer.class);

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch signalToStartReggie = new CountDownLatch(1);

        Thread jettyStarterThread = new Thread(new JettyServerStarter(signalToStartReggie));
        jettyStarterThread.setName(JettyServerStarter.class.getSimpleName());
        jettyStarterThread.start();

        CountDownLatch signalToStartRmiServer = new CountDownLatch(1);

        Thread reggieStarterThread = new Thread(new ReggieStarter(signalToStartReggie, signalToStartRmiServer));
        reggieStarterThread.setName(ReggieStarter.class.getSimpleName());
        reggieStarterThread.start();

        Thread rmiServerStarterThread = new Thread(new RMIServerStarter(signalToStartRmiServer));
        rmiServerStarterThread.setName(RMIServerStarter.class.getSimpleName());
        rmiServerStarterThread.start();

    }

    private static class RMIServerStarter implements Runnable {

        private final CountDownLatch signalToStartRmiServer;

        RMIServerStarter(CountDownLatch signalToStartRmiServer) {
            this.signalToStartRmiServer = signalToStartRmiServer;
        }

        @Override
        public void run() {

            try {
                signalToStartRmiServer.await(3, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                LOG.error("RMI Server was interrupted while waiting for Reggie to start", e);
                throw new RuntimeException(e);
            }

            try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("server-application.xml")) {
                LOG.info("The RMIServer is ready");
            }
        }

    }

}
