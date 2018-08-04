package com.swayam.demo.trx.cmt.spring.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebappInitializer implements WebApplicationInitializer {

    public static final String SPRING_APPLICATION_CONTEXT = "SPRING_APPLICATION_CONTEXT";

    @Override
    public void onStartup(ServletContext servletContext) {

	// Create the dispatcher servlet's Spring application context
	AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
	dispatcherContext.register(WebConfig.class);

	// Register and map the dispatcher servlet
	ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
	dispatcher.setLoadOnStartup(1);
	dispatcher.addMapping("/");

	servletContext.setAttribute(SPRING_APPLICATION_CONTEXT, dispatcherContext);

    }

}
