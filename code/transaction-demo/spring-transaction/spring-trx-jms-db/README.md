# About

This demoes a simple web-application, and how transaction works for a Spring Managed Transaction across disparate systems like a Database and a JMS Broker

# Running the RabbitMQ

The below command does the job:

	docker run -it -p 5672:5672 -p 15672:15672 -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin123 rabbitmq:3.7.8-management-alpine
	
We use the management plugin, so that we can use its admin console here:
	
	http://localhost:15672/
	

# How to build

The below command cleans and initializes the Postgres schema with Flyway and builds the war

	mvn clean package -P db-migration

# How to run with Jetty

## Run with Rabbit MQ
	 
	 mvn jetty:run -Dspring.profiles.active=rabbitmq
	 
## Run with Active MQ
	 
	 mvn jetty:run -Dspring.profiles.active=activemq
	 

# Accessing the API

## UI

### book.html (http://localhost:8090/spring-trx-jms-db/book.html)

This POSTs the data into a JMS Queue. It is then picked up by a JmsQueueConsumer, which does the following:

- Saves data in 2 different tables within the same Postgres schema: **genre** and **author**
- The table **genre** has an auto-increment primary key. 
- The table **author** expects an unique primary key id every time from the user
- The **author** table already contains data with Primary Keys ranging from *1* to *10*
- To simulate transaction failure (unique constraint violation), the user can enter values from *1* to *10* in the *Author Id* field
- For successful insertion the user should enter values above *10* in the *Author Id* field
- Click on the "Run in transaction mode" checkbox to see Transaction rollback in action: for successful Transaction rollback, you can see nothing is inserted into the table **genre**, though its key is printed out on the console


## REST

### Genres
	http://localhost:8090/spring-trx-jms-db/rest/genre
	
### Author
	http://localhost:8090/spring-trx-jms-db/rest/author
	
### Save Author and Genre
	http://localhost:8090/spring-trx-jms-db/rest/author-genre	
	
# Sources
		
		https://github.com/paawak/blog/tree/master/code/spring-trx-jms-db/spring-transaction/spring-trx-jms-db

# Common Problems and their solutions
## Server startup fails with BeanNotOfRequiredTypeException

This happens after adding the *@EnableAspectJAutoProxy* annotation. 
The problem is, we are using a class instead of an interface. In the *RatingDaoImpl*, replace *JdbcTemplate* with *JdbcOperations*, which is an interface 

The detailed exception is below:

```
Caused by: org.springframework.beans.factory.BeanNotOfRequiredTypeException: Bean named 'mysqlJdbcTemplate' is expected to be of type 'org.springframework.jdbc.core.JdbcTemplate' but was actually of type 'com.sun.proxy.$Proxy72'
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1148)
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1065)
	at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:818)
	at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:724)

```
		
	