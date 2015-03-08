package com.swayam.demo.rmi.server.core;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sun.jini.start.ServiceStarter;
import com.swayam.demo.rmi.server.core.reggie.ReggieStarterConfiguration;

public class SpringNonSecureRmiServer {

    public static void main(String[] args) throws InterruptedException {

        System.setProperty("java.security.policy", System.getProperty("user.home") + "/jini/policy.all");
        ServiceStarter.main(new ReggieStarterConfiguration());

        Thread.sleep(5_000);

        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("server-application.xml")) {
            System.out.println("SpringNonSecureRmiServer.main(): " + "The server is ready");
        }
    }

}
