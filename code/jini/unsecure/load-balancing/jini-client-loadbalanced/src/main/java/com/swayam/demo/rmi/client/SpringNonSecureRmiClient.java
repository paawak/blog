package com.swayam.demo.rmi.client;

import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.swayam.demo.rmi.service.BankDetailService;
import com.swayam.demo.rmi.service.LoadBalancerService;

public class SpringNonSecureRmiClient {

    public static void main(String[] args) throws Exception {

        System.setProperty("java.security.policy", System.getProperty("user.home") + "/jini/policy.all");

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("client-application.xml")) {
            LoadBalancerService loadBalancerService = context.getBean("loadBalancerService", LoadBalancerService.class);
            BankDetailService bankDetailService = loadBalancerService.lookup("BankDetailService",
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
