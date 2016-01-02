package com.swayam.demo.jini.unsecure.streaming.server.core;

import java.io.IOException;
import java.rmi.Remote;

import net.jini.core.entry.Entry;
import net.jini.core.lookup.ServiceID;
import net.jini.discovery.DiscoveryManagement;
import net.jini.export.Exporter;
import net.jini.lease.LeaseRenewalManager;
import net.jini.lookup.JoinManager;
import net.jini.lookup.entry.Name;

public class JoinManagerFactory {

    private final Exporter exporter;
    private final DiscoveryManagement discoveryManager;
    private final LeaseRenewalManager leaseRenewalManager;
    private final Remote remoteServiceImpl;
    private final Class<? extends Remote> remoteServiceInterface;

    public JoinManagerFactory(Exporter exporter, DiscoveryManagement discoveryManager, LeaseRenewalManager leaseRenewalManager, Remote remoteServiceImpl,
	    Class<? extends Remote> remoteServiceInterface) {
	this.exporter = exporter;
	this.discoveryManager = discoveryManager;
	this.leaseRenewalManager = leaseRenewalManager;
	this.remoteServiceImpl = remoteServiceImpl;
	this.remoteServiceInterface = remoteServiceInterface;
    }

    public JoinManager getJoinManager() throws IOException {
	Remote exportedService = exporter.export(remoteServiceImpl);
	return new JoinManager(exportedService, new Entry[] { new Name(remoteServiceInterface.getSimpleName()) }, (ServiceID) null, discoveryManager, leaseRenewalManager);
    }

}
