package com.swayam.demo.rmi.server.core.web;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.sun.jini.start.ServiceStarter;
import com.swayam.demo.rmi.server.core.reggie.ReggieStarterConfiguration;

public class RmiServletContextAttributeListener implements ServletContextAttributeListener {

    private static final Logger LOG = LoggerFactory.getLogger(ServletContextAttributeListener.class);

    // FIXME:: bad hack
    private static ApplicationContext applicationContext;

    @Override
    public void attributeAdded(ServletContextAttributeEvent servletContextAttributeEvent) {
        LOG.info("servletContextAttributeEvent name: `{}` value: {}", servletContextAttributeEvent.getName(), servletContextAttributeEvent.getValue());
        if (servletContextAttributeEvent.getValue() instanceof WebApplicationContext) {
            LOG.info("initializing Jini");
            String policyFilePath = RmiServletContextAttributeListener.class.getResource("/policy.all").getFile();

            LOG.info("Starting with the policy file {}", policyFilePath);

            System.setProperty("java.security.policy", policyFilePath);

            ServiceStarter.main(new ReggieStarterConfiguration(RmiServletContextAttributeListener.class.getResource("/jeri-reggie.config")));

            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                LOG.error("Reggie was interrupted while starting up", e);
                throw new RuntimeException(e);
            }

            LOG.info("**************************************Started Reggie successfully");
            applicationContext = new ClassPathXmlApplicationContext("server-application.xml");
        }
    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent servletContextAttributeEvent) {
        // do nothing
    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent servletContextAttributeEvent) {
        // do nothing
    }

    // FIXME:: bad hack
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
