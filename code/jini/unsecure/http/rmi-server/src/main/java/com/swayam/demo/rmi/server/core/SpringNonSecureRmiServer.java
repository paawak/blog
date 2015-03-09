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

        CountDownLatch signalToStartServerThread = new CountDownLatch(1);

        Thread reggieStarterThread = new Thread(new ReggieStarter(signalToStartServerThread));
        reggieStarterThread.setName(ReggieStarter.class.getSimpleName());
        // reggieStarterThread.setDaemon(true);
        reggieStarterThread.start();

        Thread rmiServerStarterThread = new Thread(new RMIServerStarter(signalToStartServerThread));
        rmiServerStarterThread.setName(RMIServerStarter.class.getSimpleName());
        rmiServerStarterThread.setDaemon(true);
        rmiServerStarterThread.start();

    }

    private static class ReggieStarter implements Runnable {

        private final CountDownLatch signalToStartServerThread;

        public ReggieStarter(CountDownLatch signalToStartServerThread) {
            this.signalToStartServerThread = signalToStartServerThread;
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

            LOG.info("Started Reggie successfully");

            signalToStartServerThread.countDown();

        }

    }

    private static class RMIServerStarter implements Runnable {

        private final CountDownLatch signalToStartServerThread;

        public RMIServerStarter(CountDownLatch signalToStartServerThread) {
            this.signalToStartServerThread = signalToStartServerThread;
        }

        @Override
        public void run() {

            // Create a basic Jetty server object that will listen on port 8080.
            // Note that if you set this to port 0
            // then a randomly available port will be assigned that you can
            // either look in the logs for the port,
            // or programmatically obtain it for use in test cases.
            Server server = new Server(8090);

            // Create the ResourceHandler. It is the object that will actually
            // handle the request for a given file. It is
            // a Jetty Handler object so it is suitable for chaining with other
            // handlers as you will see in other examples.
            ResourceHandler resource_handler = new ResourceHandler();
            // Configure the ResourceHandler. Setting the resource base
            // indicates where the files should be served out of.
            // In this example it is the current directory but it can be
            // configured to anything that the jvm has access to.
            resource_handler.setDirectoriesListed(true);
            // resource_handler.setWelcomeFiles(new String[] { "index.html" });
            resource_handler.setResourceBase("/kaaj/blog/code/jini/unsecure/http");

            // Add the ResourceHandler to the server.
            HandlerList handlers = new HandlerList();
            handlers.setHandlers(new Handler[] { resource_handler, new DefaultHandler() });
            server.setHandler(handlers);

            // Start things up! By using the server.join() the server thread
            // will join with the current thread.
            // See
            // "http://docs.oracle.com/javase/1.5.0/docs/api/java/lang/Thread.html#join()"
            // for more details.
            try {
                server.start();
                // server.join();
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            try {
                signalToStartServerThread.await(3, TimeUnit.SECONDS);
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
