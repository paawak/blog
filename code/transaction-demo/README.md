# About

This demoes a simple web-application, and how transaction works

# How to build

The below command cleans and initializes the Postgres and Mysql schema with Flyway and builds the war

	mvn clean intsall -P db-migration

# How to run with Jetty

	mvn clean jetty:run

# Accessing the API

## UI

	http://localhost:8090/transaction-demo/book.html

## REST

### Genres
	http://localhost:8090/transaction-demo/rest/genre
	
### Author
	http://localhost:8090/transaction-demo/rest/author
	
### Save Author and Genre
	http://localhost:8090/transaction-demo/rest/author-genre	
	
# Sources
		
		https://github.com/paawak/blog/tree/master/code/transaction-demo
	
# Running on Wildfly 13

## Configuring Users

To add a new user execute the add-user.sh script within the bin folder of your WildFly installation and enter the requested information.

The ManagementRealm user is manager/manager123

## Configuring JDBC Driver

To install a JDBC driver as a module you need to create a file path structure under the WILDFLY_HOME/modules, copy the JDBC driver JAR into the main/ subdirectory and create a module.xml file. The contents of module.xml is below:

``` html
<module name="jdbc.postgres" xmlns="urn:jboss:module:1.5">

    <resources>
        <resource-root path="postgresql-42.2.3.jre7.jar"/>
    </resources>
    <dependencies>
        <module name="javax.api"/>
        <module name="javax.transaction.api"/>
        <module name="javax.servlet.api" optional="true"/>
    </dependencies>
</module>
```
		
Thereafter, use the Management Console to add the JDBC Resource. After that, you can add a DataSource, XA or Non-XA

		
	