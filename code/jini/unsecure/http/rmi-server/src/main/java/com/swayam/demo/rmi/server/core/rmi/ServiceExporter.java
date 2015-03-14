package com.swayam.demo.rmi.server.core.rmi;

import java.rmi.Remote;

import net.jini.core.entry.Entry;
import net.jini.core.lookup.ServiceID;
import net.jini.discovery.DiscoveryManagement;
import net.jini.export.Exporter;
import net.jini.jeri.BasicJeriExporter;
import net.jini.jeri.http.HttpServerEndpoint;
import net.jini.lease.LeaseRenewalManager;
import net.jini.lookup.JoinManager;
import net.jini.lookup.entry.Name;

import org.springframework.beans.factory.InitializingBean;

public class ServiceExporter implements InitializingBean {

    private DiscoveryManagement discoveryManager;
    private LeaseRenewalManager leaseRenewalManager;
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
        Exporter exporter = getExporter();
        Remote exportedService = exporter.export(service);
        @SuppressWarnings("unused")
        JoinManager joinManager = new JoinManager(exportedService, new Entry[] { new Name(serviceName) }, (ServiceID) null, discoveryManager, leaseRenewalManager);
    }

    private Exporter getExporter() {
        return new BasicJeriExporter(HttpServerEndpoint.getInstance(0), new BasicILFactoryWithLogging());
        // return new BasicJeriExporter(new JettyServerEndpoint(8100), new
        // BasicILFactoryWithLogging());
    }

}
