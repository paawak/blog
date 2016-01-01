package com.swayam.demo.jini.unsecure.streaming.client;

import java.rmi.Remote;

import net.jini.core.discovery.LookupLocator;
import net.jini.core.entry.Entry;
import net.jini.core.lookup.ServiceTemplate;
import net.jini.lookup.entry.Name;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

public class ProxyRemoteFactoryBean implements FactoryBean<Remote>, InitializingBean {

    private LookupLocator lookupLocator;
    private String serviceName;
    private Remote remoteService;

    @Override
    public Remote getObject() throws Exception {
        return remoteService;
    }

    @Override
    public Class<?> getObjectType() {
        return Remote.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        remoteService = (Remote) lookupLocator.getRegistrar().lookup(new ServiceTemplate(null, null, new Entry[] { new Name(serviceName) }));
    }

    public LookupLocator getLookupLocator() {
        return lookupLocator;
    }

    public void setLookupLocator(LookupLocator lookupLocator) {
        this.lookupLocator = lookupLocator;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

}
