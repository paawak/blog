package com.swayam.demo.rmi.server.core.jetty;

import java.util.concurrent.CountDownLatch;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JettyServerStarter implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(JettyServerStarter.class);

    private final CountDownLatch signalToStartReggie;

    public JettyServerStarter(CountDownLatch signalToStart) {
        this.signalToStartReggie = signalToStart;
    }

    @Override
    public void run() {

        Server server = new Server(8100);

        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);
        handler.addServletWithMapping(RmiServlet.class, "/*");

        try {
            server.start();
            LOG.info("**************************************The WebSever is ready");
        } catch (Exception e) {
            LOG.error("Error starting Jetty Server", e);
            throw new RuntimeException(e);
        }

        signalToStartReggie.countDown();
    }
}