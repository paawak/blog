package com.swayam.demo.rmi.server.impl;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import net.jini.core.discovery.LookupLocator;
import net.jini.core.entry.Entry;
import net.jini.core.lookup.ServiceTemplate;
import net.jini.lookup.entry.Name;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swayam.demo.rmi.service.LoadBalancerService;

@Service("loadBalancerService")
public class LoadBalancerServiceImpl implements LoadBalancerService {

    private final LookupLocator serverLookupLocator;

    @Autowired
    public LoadBalancerServiceImpl(LookupLocator serverLookupLocator) {
        this.serverLookupLocator = serverLookupLocator;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Remote> T lookup(String serviceName, Class<T> remoteClass) throws RemoteException {
        try {
            T remoteService = (T) serverLookupLocator.getRegistrar().lookup(
                    new ServiceTemplate(null, new Class[] { remoteClass }, new Entry[] { new Name(serviceName) }));

            return remoteService;
        } catch (ClassNotFoundException | IOException e) {
            throw new RemoteException("Failed to lookup remote service: " + serviceName, e);
        }
    }

}
