package com.swayam.demo.rmi.server.core;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringNonSecureRmiServer {

    private static final String CODE_BASE_URL = "http://localhost:8080/";
    private static final String[] JARS_DOWNLOADED_DYNAMICALLY = new String[] { "rmi-service-api-1.0.jar" };

    private static String getRmiServerCodebase() {

        StringBuilder urlBuilder = new StringBuilder(200);

        // the urls are separated by space
        for (String jar : JARS_DOWNLOADED_DYNAMICALLY) {
            urlBuilder.append(CODE_BASE_URL).append(jar).append(" ");
        }

        urlBuilder.setLength(urlBuilder.length() - 1);

        return urlBuilder.toString();

    }

    public static void main(String[] args) {
        String codebase = getRmiServerCodebase();
        // set the below property to enable dynamic code download
        System.setProperty("java.rmi.server.codebase", codebase);
        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("server-application.xml")) {
            System.out.println("SpringNonSecureRmiServer.main(): " + "The server is ready");
        }
    }

}
