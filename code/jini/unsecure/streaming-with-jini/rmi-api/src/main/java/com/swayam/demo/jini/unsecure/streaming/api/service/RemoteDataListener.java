package com.swayam.demo.jini.unsecure.streaming.api.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteDataListener<T> extends Remote {

    void newData(T data) throws RemoteException;

    void endOfData() throws RemoteException;

}
