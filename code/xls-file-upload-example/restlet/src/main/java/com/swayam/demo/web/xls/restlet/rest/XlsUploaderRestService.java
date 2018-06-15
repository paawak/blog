package com.swayam.demo.web.xls.restlet.rest;

import java.util.List;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.fileupload.RestletFileUpload;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.swayam.demo.web.xls.restlet.service.XlsReader;

public class XlsUploaderRestService extends ServerResource {

	@Post
	public Representation accept(Representation entity) throws Exception {
		Representation result = null;
		if (entity != null && MediaType.MULTIPART_FORM_DATA.equals(entity.getMediaType(), true)) {
			// 1/ Create a factory for disk-based file items
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(1000240);

			// 2/ Create a new file upload handler based on the Restlet
			// FileUpload extension that will parse Restlet requests and
			// generates FileItems.
			RestletFileUpload upload = new RestletFileUpload(factory);

			// 3/ Request is parsed by the handler which generates a
			// list of FileItems
			FileItemIterator fileIterator = upload.getItemIterator(entity);

			// Process only the uploaded item called "fileToUpload"
			// and return back
			boolean found = false;
			while (fileIterator.hasNext() && !found) {
				FileItemStream fi = fileIterator.next();

				if (fi.getFieldName().equals("file")) {
					// For the matter of sample code, it filters the multo
					// part form according to the field name.
					found = true;
					// consume the stream immediately, otherwise the stream
					// will be closed.
					StringBuilder sb = new StringBuilder("media type: ");
					sb.append(fi.getContentType()).append("\n");
					sb.append("file name : ");
					sb.append(fi.getName()).append("\n");

					List<String> items = new XlsReader().read(fi.openStream());

					for (String item : items) {
						sb.append(item);
					}
					sb.append("\n");
					result = new StringRepresentation(sb.toString(), MediaType.TEXT_PLAIN);
				}
			}
		} else {
			// POST request with no entity.
			setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
		}

		return result;
	}

}
