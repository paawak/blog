package com.swayam.demo.rmi.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BankAccountService extends Remote {

    long createBankAccount(String userName) throws RemoteException;

}
