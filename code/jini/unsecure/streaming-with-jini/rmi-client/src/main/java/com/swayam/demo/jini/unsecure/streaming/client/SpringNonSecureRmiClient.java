package com.swayam.demo.jini.unsecure.streaming.client;

import java.awt.EventQueue;
import java.rmi.RemoteException;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.swayam.demo.jini.unsecure.streaming.api.dto.BankDetail;
import com.swayam.demo.jini.unsecure.streaming.api.service.BankDetailService;
import com.swayam.demo.jini.unsecure.streaming.api.service.RemoteDataListener;

import net.jini.export.Exporter;
import net.jini.jeri.BasicILFactory;
import net.jini.jeri.BasicJeriExporter;
import net.jini.jeri.tcp.TcpServerEndpoint;

public class SpringNonSecureRmiClient {

    public static void main(String[] args) throws Exception {

	System.setProperty("java.security.policy", System.getProperty("user.home") + "/jini/policy.all");

	if (System.getSecurityManager() == null) {
	    System.setSecurityManager(new SecurityManager());
	}

	try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("client-application.xml")) {
	    BankDetailService bankDetailService = context.getBean("bankDetailService", BankDetailService.class);
	    streamData(bankDetailService);
	    showFrame(bankDetailService);
	}

    }

    private static void streamData(BankDetailService bankDetailService) throws RemoteException {

	Exporter exporter = new BasicJeriExporter(TcpServerEndpoint.getInstance(0), new BasicILFactory());

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
