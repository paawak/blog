package com.swayam.demo.web.xls.restlet.rest;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class XlsFileUploadApplication extends Application {

	@Override
	public synchronized Restlet createRoot() {
		// Create a router Restlet that routes each call to a new instance of
		// HelloWorldResource.
		Router router = new Router(getContext());

		// Defines only one route
		router.attach("/hello", XlsUploaderRestService.class);

		return router;
	}

}
