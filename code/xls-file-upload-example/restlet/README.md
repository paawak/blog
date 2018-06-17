# About

This is a demo for uploding Microsoft XLS files via the Restlet-based REST service. This works with *jboss-5.1.0.GA*, deployed on *JDK-6*.

# Restlet Framework links

* https://restlet.com/open-source/documentation/user-guide/2.2/introduction/getting-started/maven
* https://restlet.com/open-source/documentation/user-guide/2.2/editions/jee/overview#run-in-a-servlet-container
* https://restlet.com/open-source/documentation/user-guide/2.2/extensions/jaxrs
* https://restlet.com/open-source/documentation/user-guide/2.2/extensions/fileupload

# Restlet Maven Repo
http://maven.restlet.com

# Testing

The below command should do the job:

	curl -v -X POST -H "content-type:multipart/form-data" -F file=@src/test/resources/xls/test-simple.xls http://localhost:8080/xls-file-upload-example/rest/xls/upload  

You can also test it through its index page, this uses pure HTML form to upload the xls file.

	
	