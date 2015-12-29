package com.swayam.demo.rmi.server.core;

import java.rmi.Remote;

import net.jini.core.discovery.LookupLocator;
import net.jini.core.entry.Entry;
import net.jini.core.lookup.ServiceID;
import net.jini.discovery.DiscoveryManagement;
import net.jini.discovery.LookupLocatorDiscovery;
import net.jini.export.Exporter;
import net.jini.jrmp.JrmpExporter;
import net.jini.lease.LeaseRenewalManager;
import net.jini.lookup.JoinManager;
import net.jini.lookup.entry.Name;

import com.swayam.demo.rmi.server.impl.BankAccountServiceImpl;
import com.swayam.demo.rmi.server.impl.UserServiceImpl;
import com.swayam.demo.rmi.service.BankAccountService;
import com.swayam.demo.rmi.service.UserService;

public class SimpleNonSecureRmiServer {
	
    private final DiscoveryManagement discoveryManager;
    private final LeaseRenewalManager leaseRenewalManager;

    public SimpleNonSecureRmiServer() throws Exception {
        discoveryManager = new LookupLocatorDiscovery(
                new LookupLocator[] { new LookupLocator("jini://localhost:4160") });
        leaseRenewalManager = new LeaseRenewalManager();
    }

    private void exportAndJoinServices() throws Exception {
        exportAndJoinService(new UserServiceImpl(), discoveryManager, leaseRenewalManager,
                UserService.class.getSimpleName());
        exportAndJoinService(new BankAccountServiceImpl(), discoveryManager, leaseRenewalManager,
                BankAccountService.class.getSimpleName());
    }

    private void exportAndJoinService(Remote service, DiscoveryManagement discoveryManager,
            LeaseRenewalManager leaseRenewalManager, String serviceName) throws Exception {
        // the exporter can export only 1 remote service
        // this is VERY important: prevents the class from getting GC-ed
        Exporter exporter = getExporter();
        Remote exportedService = exporter.export(service);
        JoinManager joinManager = new JoinManager(exportedService, new Entry[] { new Name(serviceName) },
                (ServiceID) null, discoveryManager, leaseRenewalManager);
    }

    private Exporter getExporter() {
        return new JrmpExporter();
        // return new BasicJeriExporter(TcpServerEndpoint.getInstance(0), new
        // BasicILFactory());
    }

    public static void main(String[] args) throws Exception {
        SimpleNonSecureRmiServer server = new SimpleNonSecureRmiServer();
        server.exportAndJoinServices();
        System.out.println("Hello server is ready");
    }

}
