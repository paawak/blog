package com.swayam.demo.web.xls.restlet.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.swayam.demo.web.xls.restlet.service.XlsReader;

@Path("/xls")
public class XlsUploaderRestService {

	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadFile(@FormDataParam("file") InputStream inputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {

		XlsReader xlsReader = new XlsReader();

		try {
			List<String> result = xlsReader.read(inputStream);
			return Response.status(200).entity(result).build();
		} catch (IOException e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		}

	}

}
