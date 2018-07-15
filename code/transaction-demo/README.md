# About

This demoes a simple web-application, and how transaction works

# How to build

The below command cleans and initializes the Postgres and Mysql schema with Flyway and builds the war

	mvn clean intsall -P db-migration

# How to run with Jetty

	mvn clean jetty:run

# Accessing the API

## UI

	http://localhost:8090/transaction-demo/author.html

## REST

### Genres
	http://localhost:8090/transaction-demo/genre
	
### Author
	http://localhost:8090/transaction-demo/author	


	
# Sources
		
		https://github.com/paawak/blog/tree/master/code/transaction-demo
	
	