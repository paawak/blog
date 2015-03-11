package com.swayam.demo.rmi.server.core.http;

import java.util.concurrent.CountDownLatch;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
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