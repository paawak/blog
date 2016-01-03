package com.swayam.demo.jini.unsecure.streaming.client;

import java.rmi.RemoteException;

import com.swayam.demo.jini.unsecure.streaming.api.dto.BankDetail;
import com.swayam.demo.jini.unsecure.streaming.api.service.BankDetailService;
import com.swayam.demo.jini.unsecure.streaming.api.service.RemoteDataListener;

import net.jini.export.Exporter;
import net.jini.jeri.BasicILFactory;
import net.jini.jeri.BasicJeriExporter;
import net.jini.jeri.tcp.TcpServerEndpoint;

public class SimpleStreamingClient {

    public static void main(String[] args) throws RemoteException {
	SpringContextHelper springContextHelper = new SpringContextHelper();
	RemoteDataListener<BankDetail> remoteDataListener = new RemoteDataListenerConsolePrinterImpl<>();

	Exporter exporter = new BasicJeriExporter(TcpServerEndpoint.getInstance(0), new BasicILFactory());
	@SuppressWarnings("unchecked")
	RemoteDataListener<BankDetail> exportedRemoteDataListener = (RemoteDataListener<BankDetail>) exporter.export(remoteDataListener);
	BankDetailService bankDetailService = springContextHelper.getBankDetailService();
	bankDetailService.streamAllBankDetails(exportedRemoteDataListener);
    }

}
