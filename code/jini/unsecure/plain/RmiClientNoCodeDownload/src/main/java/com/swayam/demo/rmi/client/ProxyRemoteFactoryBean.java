package com.swayam.demo.rmi.client;

import java.rmi.Remote;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import net.jini.core.discovery.LookupLocator;
import net.jini.core.entry.Entry;
import net.jini.core.lookup.ServiceTemplate;
import net.jini.lookup.entry.Name;

public class ProxyRemoteFactoryBean implements FactoryBean<Remote>, InitializingBean {

    private LookupLocator lookupLocator;
    private Class<?> serviceInterface;
    private String serviceName;
    private Remote remoteService;

    @Override
    public Remote getObject() throws Exception {
        return remoteService;
    }

    @Override
    public Class<?> getObjectType() {
        return serviceInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        remoteService = (Remote) lookupLocator.getRegistrar().lookup(new ServiceTemplate(null,
                new Class[]{serviceInterface}, new Entry[]{new Name(serviceName)}));
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

    public Class<?> getServiceInterface() {
        return serviceInterface;
    }

    public void setServiceInterface(Class<?> serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

}
