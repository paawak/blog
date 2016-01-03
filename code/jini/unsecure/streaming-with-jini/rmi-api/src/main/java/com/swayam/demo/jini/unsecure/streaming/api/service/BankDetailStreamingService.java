package com.swayam.demo.jini.unsecure.streaming.api.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.swayam.demo.jini.unsecure.streaming.api.dto.BankDetail;

public interface BankDetailStreamingService extends Remote {

    void streamAllBankDetails(RemoteDataListener<BankDetail> bankDetailRemoteListener) throws RemoteException;

}