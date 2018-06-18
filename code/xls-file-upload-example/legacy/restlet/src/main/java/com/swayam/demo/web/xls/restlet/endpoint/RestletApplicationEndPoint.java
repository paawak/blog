package com.swayam.demo.web.xls.restlet.endpoint;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import com.swayam.demo.web.xls.restlet.rest.XlsUploaderRestService;

public class RestletApplicationEndPoint extends Application {

	@Override
	public synchronized Restlet createInboundRoot() {

		Router router = new Router(getContext());

		router.attach("/upload", XlsUploaderRestService.class);

		return router;
	}

}
