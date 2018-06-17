package com.swayam.demo.web.xls.restlet.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("health")
public class HealthCheckRestService {

	@Produces(MediaType.TEXT_PLAIN)
	@GET
	public String hello() {
		return "hello, world";
	}

}
