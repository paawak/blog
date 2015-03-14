package com.swayam.demo.rmi.server.core.http;

import java.util.concurrent.CountDownLatch;

import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JettyServerStarter implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(JettyServerStarter.class);

    private final CountDownLatch signalToStartReggie;

    public JettyServerStarter(CountDownLatch signalToStart) {
        this.signalToStartReggie = signalToStart;
    }

    @Override
    public void run() {

        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("jetty-spring.xml")) {
            Server server = context.getBean(Server.class);
            server.start();
            LOG.info("The WebSever is ready");
        } catch (Exception e) {
            LOG.error("error starting web server", e);
        }

        signalToStartReggie.countDown();
    }
}