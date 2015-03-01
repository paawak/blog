package com.swayam.demo.rmi.server.core;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringNonSecureRmiServer {

    public static void main(String[] args) {
        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("server-application.xml")) {
            System.out.println("SpringNonSecureRmiServer.main(): " + "The server is ready");
        }
    }

}
