package com.swayam.demo.rmi.client;

import java.awt.EventQueue;
import java.rmi.Remote;
import java.rmi.server.RMIClassLoaderSpi;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringNonSecureRmiClient {

    public static void main(String[] args) throws Exception {

        System.setProperty("java.security.policy", System.getProperty("user.home") + "/jini/policy.all");

        // the below line is put only for debugging purposes, its not needed, as
        // the default class loader is good enough
        System.setProperty(RMIClassLoaderSpi.class.getName(), DelegatingRMIClassLoader.class.getName());

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("client-application.xml")) {
            Remote bankDetailService = context.getBean("bankDetailService", Remote.class);
            showFrame(bankDetailService);
        }

    }

    private static void showFrame(Remote bankDetailService) {
        for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        }

        EventQueue.invokeLater(() -> {
            new GroupingDemoFrame(bankDetailService).setVisible(true);
        });
    }

}
