package com.swayam.demo.rmi.server.core.rmi;

import java.rmi.Remote;

import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;

import net.jini.core.entry.Entry;
import net.jini.core.lookup.ServiceID;
import net.jini.discovery.DiscoveryManagement;
import net.jini.export.Exporter;
import net.jini.id.Uuid;
import net.jini.id.UuidFactory;
import net.jini.jeri.BasicILFactory;
import net.jini.jeri.BasicJeriExporter;
import net.jini.jeri.http.HttpServerEndpoint;
import net.jini.lease.LeaseRenewalManager;
import net.jini.lookup.JoinManager;
import net.jini.lookup.entry.Name;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.swayam.demo.rmi.server.core.jini.http.HttpBasedILFactory;
import com.swayam.demo.rmi.server.core.jini.http.HttpServerEndpoint2;
import com.swayam.demo.rmi.server.core.jini.servlet.ServletBasedILFactory;
import com.swayam.demo.rmi.server.core.jini.servlet.ServletBasedServerEndpoint;
import com.swayam.demo.rmi.shared.jini.http.HttpSocketFactory;

public class ServiceExporter implements InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceExporter.class);

    private static final String SERVER_URL = "http://localhost:8080/RmiServer";

    public static final String REMOTE_METHOD_INVOCATION_URI = "/INVOKE_REMOTE_METHOD";

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
        Uuid uuid = UuidFactory.generate();
        ServiceID serviceID = new ServiceID(uuid.getMostSignificantBits(), uuid.getLeastSignificantBits());
        @SuppressWarnings("unused")
        JoinManager joinManager = new JoinManager(exportedService, new Entry[] { new Name(serviceName) }, serviceID, discoveryManager, leaseRenewalManager);
        LOG.info("----------------------- Service: {} exported with serviceID: {}", serviceName, serviceID);
    }

    private Exporter getExporter() {
        if (false) {
            return getDefultExporter();
        } else {
            return getCustomExporter();
        }
    }

    private Exporter getDefultExporter() {
        SocketFactory socketFactory = new HttpSocketFactory();
        ServerSocketFactory serverSocketFactory = ServerSocketFactory.getDefault();
        return new BasicJeriExporter(HttpServerEndpoint.getInstance("localhost", 0, socketFactory, serverSocketFactory), new BasicILFactory());
    }

    private Exporter getCustomExporter() {
        if (false) {
            return new BasicJeriExporter(HttpServerEndpoint2.getInstance("localhost", 8899), new HttpBasedILFactory());
        }
        return new BasicJeriExporter(new ServletBasedServerEndpoint(SERVER_URL), new ServletBasedILFactory(SERVER_URL + REMOTE_METHOD_INVOCATION_URI));
    }

}
