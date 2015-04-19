package com.swayam.demo.rmi.server.core.web;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;

public class RmiServletContextAttributeListener implements ServletContextAttributeListener {

    private static final Logger LOG = LoggerFactory.getLogger(ServletContextAttributeListener.class);

    // FIXME:: bad hack
    private static ApplicationContext applicationContext;

    @Override
    public void attributeAdded(ServletContextAttributeEvent servletContextAttributeEvent) {
        LOG.info("servletContextAttributeEvent name: `{}` value: {}", servletContextAttributeEvent.getName(), servletContextAttributeEvent.getValue());
        if (servletContextAttributeEvent.getValue() instanceof WebApplicationContext) {
            LOG.info("initializing Jini-beans");
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
