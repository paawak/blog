package com.swayam.demo.rmi.client;

import java.awt.EventQueue;
import java.rmi.server.RMIClassLoaderSpi;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.swayam.demo.rmi.service.BankDetailService;

public class SpringNonSecureRmiClient {

    public static void main(String[] args) throws Exception {

        System.setProperty("java.security.policy", SpringNonSecureRmiClient.class.getResource("/policy.all").getPath());

        // the below line is put only for debugging purposes, its not needed, as
        // the default class loader is good enough
        System.setProperty(RMIClassLoaderSpi.class.getName(), MyClassProvider.class.getName());

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
            System.out.println("DynamicDownloadRmiClient.main() setting custom security manager");
        }

        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("client-application.xml")) {
            BankDetailService bankDetailService = (BankDetailService) context.getBean("bankDetailService");
            showFrame(bankDetailService);
        }

    }

    private static void showFrame(BankDetailService bankDetailService) {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
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
