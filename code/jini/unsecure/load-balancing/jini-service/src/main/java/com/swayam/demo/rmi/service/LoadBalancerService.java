package com.swayam.demo.rmi.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LoadBalancerService extends Remote {

    <T extends Remote> T lookup(String serviceName, Class<T> remoteClass) throws RemoteException;

}
