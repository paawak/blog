package com.swayam.demo.rmi.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface UserService extends Remote {

    List<String> getCurrentUsers() throws RemoteException;

}
