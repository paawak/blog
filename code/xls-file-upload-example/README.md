# About

This is a demo for uploding Microsoft XLS files via the Jersey-based REST service. This works with *jboss-5.1.0.GA*, deployed on *JDK-6*.

# Testing

The below command should do the job:

	curl -v -X POST -H "content-type:multipart/form-data" -F file=@src/test/resources/xls/test-simple.xls http://localhost:8080/xls-file-upload-example/rest/xls/upload  



	
	