package com.swayam.demo.web.xls.restlet.rest;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class XlsFileUploadApplication extends Application {

	@Override
	public synchronized Restlet createInboundRoot() {
		Router router = new Router(getContext());

		router.attach("/upload", XlsUploaderRestService.class);
		router.attach("/health", HealthCheckRestService.class);

		return router;
	}

}
