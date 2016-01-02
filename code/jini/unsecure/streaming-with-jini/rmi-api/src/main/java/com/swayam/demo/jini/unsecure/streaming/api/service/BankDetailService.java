package com.swayam.demo.jini.unsecure.streaming.api.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import com.swayam.demo.jini.unsecure.streaming.api.dto.BankDetail;
import com.swayam.demo.jini.unsecure.streaming.api.dto.BankDetailGroups;

public interface BankDetailService extends Remote {

    Map<String, List<BankDetail>> getBankDetails(BankDetailGroups group) throws RemoteException;

    void streamAllBankDetails(RemoteDataListener<BankDetail> bankDetailRemoteListener) throws RemoteException;

}