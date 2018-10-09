package com.swayam.demo.trx.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebappInitializer implements WebApplicationInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebappInitializer.class);

    private static final String SPRING_PROFILE_KEY = "spring.profiles.active";

    @Override
    public void onStartup(ServletContext servletContext) {

	String activeProfile = System.getProperty(SPRING_PROFILE_KEY);

	if (activeProfile == null) {
	    activeProfile = "wildfly";
	    LOGGER.warn("************* No property with the key {} found, using the default value: {}", SPRING_PROFILE_KEY, activeProfile);
	} else {
	    LOGGER.info("************* starting server with {}: {}", SPRING_PROFILE_KEY, activeProfile);
	}

	servletContext.setInitParameter(SPRING_PROFILE_KEY, activeProfile);

	// Create the dispatcher servlet's Spring application context
	AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
	dispatcherContext.register(WebConfig.class);

	// Register and map the dispatcher servlet
	ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
	dispatcher.setLoadOnStartup(1);
	dispatcher.addMapping("/");

    }

}
