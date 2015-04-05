package com.swayam.demo.rmi.shared.api.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import com.swayam.demo.rmi.shared.api.dto.BankDetail;
import com.swayam.demo.rmi.shared.api.dto.BankDetailGroups;

public interface BankDetailService extends Remote {

    Map<String, List<BankDetail>> getBankDetails(BankDetailGroups group) throws RemoteException;

}