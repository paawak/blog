package com.swayam.demo.rmi.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import com.swayam.demo.rmi.dto.BankDetail;
import com.swayam.demo.rmi.dto.BankDetailGroups;

public interface BankDetailService extends Remote {

    Map<String, List<BankDetail>> getBankDetails(BankDetailGroups group) throws RemoteException;

    Map<String, List<BankDetail>> getBankDetailsForJob() throws RemoteException;

}