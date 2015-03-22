package com.swayam.demo.rmi.server.core.http;

import java.util.concurrent.CountDownLatch;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
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

        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");
        String pathToWarFile = JettyServerStarter.class.getResource("/web-server.war").getFile();
        LOG.info("################# serving the war file: {}", pathToWarFile);
        webapp.setWar(pathToWarFile);
        server.setHandler(webapp);

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