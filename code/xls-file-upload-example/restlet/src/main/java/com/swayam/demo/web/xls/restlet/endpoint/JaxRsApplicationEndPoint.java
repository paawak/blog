package com.swayam.demo.web.xls.restlet.endpoint;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.restlet.Context;
import org.restlet.ext.jaxrs.JaxRsApplication;

import com.swayam.demo.web.xls.restlet.rest.HealthCheckRestService;

public class JaxRsApplicationEndPoint extends JaxRsApplication {

    public JaxRsApplicationEndPoint(Context context) {
	super(context);
	add(new JaxRsRestApplication());
    }

    private class JaxRsRestApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
	    Set<Class<?>> classes = new HashSet<Class<?>>();
	    classes.add(HealthCheckRestService.class);
	    return classes;
	}

    }

}
