package com.swayam.demo.rmi.server.core;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sun.jini.start.ServiceStarter;
import com.swayam.demo.rmi.server.core.reggie.ReggieStarterConfiguration;

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

    private static class JettyServerStarter implements Runnable {

        private final CountDownLatch signalToStartReggie;

        JettyServerStarter(CountDownLatch signalToStart) {
            this.signalToStartReggie = signalToStart;
        }

        @Override
        public void run() {

            Server server = new Server(8100);

            ResourceHandler resourceHandler = new ResourceHandler();
            resourceHandler.setDirectoriesListed(true);
            // resourceHandler.setWelcomeFiles(new String[] { "index.html" });
            resourceHandler.setResourceBase(".");

            // Add the ResourceHandler to the server.
            HandlerList handlers = new HandlerList();
            handlers.setHandlers(new Handler[] { resourceHandler, new DefaultHandler() });
            server.setHandler(handlers);

            try {
                server.start();
            } catch (Exception e) {
                LOG.error("Error starting Jetty Server", e);
                throw new RuntimeException(e);
            }

            signalToStartReggie.countDown();
        }
    }

    private static class ReggieStarter implements Runnable {

        private final CountDownLatch signalToStartReggie;
        private final CountDownLatch signalToStartRmiServer;

        ReggieStarter(CountDownLatch signalToStartReggie, CountDownLatch signalToStartRmiServer) {
            this.signalToStartReggie = signalToStartReggie;
            this.signalToStartRmiServer = signalToStartRmiServer;
        }

        @Override
        public void run() {

            try {
                signalToStartReggie.await(3, TimeUnit.SECONDS);
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

            LOG.info("Started Reggie successfully");

            signalToStartRmiServer.countDown();

        }

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
