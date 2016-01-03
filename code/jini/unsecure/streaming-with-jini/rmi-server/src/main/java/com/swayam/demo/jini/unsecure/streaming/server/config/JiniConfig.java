package com.swayam.demo.jini.unsecure.streaming.server.config;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

import com.swayam.demo.jini.unsecure.streaming.api.service.BankDetailService;
import com.swayam.demo.jini.unsecure.streaming.api.service.BankDetailStreamingService;
import com.swayam.demo.jini.unsecure.streaming.server.core.BasicILFactoryWithLogging;
import com.swayam.demo.jini.unsecure.streaming.server.core.JoinManagerFactory;

import net.jini.core.discovery.LookupLocator;
import net.jini.discovery.DiscoveryManagement;
import net.jini.discovery.LookupLocatorDiscovery;
import net.jini.export.Exporter;
import net.jini.jeri.BasicJeriExporter;
import net.jini.jeri.tcp.TcpServerEndpoint;
import net.jini.lease.LeaseRenewalManager;
import net.jini.lookup.JoinManager;

@Configuration
public class JiniConfig {

    @Autowired
    private Environment environment;

    @Bean
    public LookupLocatorDiscovery discoveryManager() throws MalformedURLException {
	return new LookupLocatorDiscovery(new LookupLocator[] { new LookupLocator(environment.getProperty("jini.registryUrl")) });
    }

    @Bean
    public LeaseRenewalManager leaseRenewalManager() {
	return new LeaseRenewalManager();
    }

    @Bean
    public JoinManager bankDetailService(Exporter exporter, DiscoveryManagement discoveryManager, LeaseRenewalManager leaseRenewalManager, BankDetailService bankDetailService) throws IOException {
	return new JoinManagerFactory(exporter, discoveryManager, leaseRenewalManager, bankDetailService, BankDetailService.class).getJoinManager();
    }

    @Bean
    public JoinManager bankDetailStreamingService(Exporter exporter, DiscoveryManagement discoveryManager, LeaseRenewalManager leaseRenewalManager,
	    BankDetailStreamingService bankDetailStreamingService) throws IOException {
	return new JoinManagerFactory(exporter, discoveryManager, leaseRenewalManager, bankDetailStreamingService, BankDetailStreamingService.class).getJoinManager();
    }

    @Bean
    @Scope("prototype")
    public Exporter exporter() {
	return new BasicJeriExporter(TcpServerEndpoint.getInstance(Integer.valueOf(environment.getProperty("jini.rmiServerPort"))), new BasicILFactoryWithLogging());
    }

}
