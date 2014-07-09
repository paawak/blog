package com.swayam.demo.rmi.core;

import java.rmi.Remote;

import org.springframework.beans.factory.InitializingBean;

import net.jini.core.entry.Entry;
import net.jini.core.lookup.ServiceID;
import net.jini.discovery.DiscoveryManagement;
import net.jini.export.Exporter;
import net.jini.lease.LeaseRenewalManager;
import net.jini.lookup.JoinManager;
import net.jini.lookup.entry.Name;

public class ServiceExporter implements InitializingBean {

    private DiscoveryManagement discoveryManager;
    private LeaseRenewalManager leaseRenewalManager;
    private Exporter exporter;
    private Remote service;
    private String serviceName;

    public DiscoveryManagement getDiscoveryManager() {
        return discoveryManager;
    }

    public void setDiscoveryManager(DiscoveryManagement discoveryManager) {
        this.discoveryManager = discoveryManager;
    }

    public LeaseRenewalManager getLeaseRenewalManager() {
        return leaseRenewalManager;
    }

    public void setLeaseRenewalManager(LeaseRenewalManager leaseRenewalManager) {
        this.leaseRenewalManager = leaseRenewalManager;
    }

    public Exporter getExporter() {
        return exporter;
    }

    public void setExporter(Exporter exporter) {
        this.exporter = exporter;
    }

    public Remote getService() {
        return service;
    }

    public void setService(Remote service) {
        this.service = service;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Remote exportedService = exporter.export(service);
        JoinManager joinManager = new JoinManager(exportedService, new Entry[]{new Name(serviceName)},
                (ServiceID) null, discoveryManager, leaseRenewalManager);
    }

}
