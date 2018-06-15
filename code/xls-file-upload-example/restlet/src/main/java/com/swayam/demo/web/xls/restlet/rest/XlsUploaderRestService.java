package com.swayam.demo.web.xls.restlet.rest;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class XlsUploaderRestService extends ServerResource {

	@Get
	public String hello() {
		return "hello, world";
	}

}
