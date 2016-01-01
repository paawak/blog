package com.swayam.demo.jini.unsecure.streaming.client;

import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.swayam.demo.jini.unsecure.streaming.api.service.BankDetailService;

public class SpringNonSecureRmiClient {

    public static void main(String[] args) throws Exception {

        System.setProperty("java.security.policy", System.getProperty("user.home") + "/jini/policy.all");

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("client-application.xml")) {
            BankDetailService bankDetailService =
                    context.getBean("bankDetailService",
                            BankDetailService.class);
            showFrame(bankDetailService);
        }

    }

    private static void showFrame(BankDetailService bankDetailService) {
        for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                        | UnsupportedLookAndFeelException e) {
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
