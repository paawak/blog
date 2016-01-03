package com.swayam.demo.jini.unsecure.streaming.client;

import java.awt.EventQueue;
import java.rmi.RemoteException;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.swayam.demo.jini.unsecure.streaming.api.dto.BankDetail;
import com.swayam.demo.jini.unsecure.streaming.api.service.BankDetailService;
import com.swayam.demo.jini.unsecure.streaming.api.service.RemoteDataListener;
import com.swayam.demo.jini.unsecure.streaming.client.config.RmiClientConfig;

import net.jini.export.Exporter;

public class SpringNonSecureRmiClient {

    private static final Logger LOG = LoggerFactory.getLogger(SpringNonSecureRmiClient.class);

    public static void main(String[] args) throws Exception {

	String policyFilePath = SpringNonSecureRmiClient.class.getResource("/policy.all").getFile();

	LOG.info("Starting with the policy file {}", policyFilePath);

	System.setProperty("java.security.policy", policyFilePath);

	if (System.getSecurityManager() == null) {
	    System.setSecurityManager(new SecurityManager());
	}

	try (ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(RmiClientConfig.class)) {
	    BankDetailService bankDetailService = context.getBean("bankDetailService", BankDetailService.class);
	    Exporter exporter = context.getBean(Exporter.class);
	    streamData(bankDetailService, exporter);
	    showFrame(bankDetailService);
	}

    }

    private static void streamData(BankDetailService bankDetailService, Exporter exporter) throws RemoteException {

	RemoteDataListener<BankDetail> remoteDataListener = new RemoteDataListenerConsolePrinterImpl<>();

	@SuppressWarnings("unchecked")
	RemoteDataListener<BankDetail> exportedRemoteDataListener = (RemoteDataListener<BankDetail>) exporter.export(remoteDataListener);

	bankDetailService.streamAllBankDetails(exportedRemoteDataListener);

    }

    private static void showFrame(BankDetailService bankDetailService) {
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
