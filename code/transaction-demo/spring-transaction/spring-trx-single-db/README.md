# About

This demoes a simple web-application, and how transaction works for a Spring Managed Transaction within a single DB, across multiple tables

# How to build

The below command cleans and initializes the Postgres schema with Flyway and builds the war

	mvn clean package -P db-migration

# How to run with Jetty

	 mvn jetty:run

# Accessing the API

## UI

### book.html (http://localhost:8090/spring-trx-single-db/book.html)
- Saves data in 2 different tables within the same Postgres schema: **genre** and **author**
- The table **genre** has an auto-increment primary key. 
- The table **author** expects an unique primary key id every time from the user
- The **author** table already contains data with Primary Keys ranging from *1* to *10*
- To simulate transaction failure (unique constraint violation), the user can enter values from *1* to *10* in the *Author Id* field
- For successful insertion the user should enter values above *10* in the *Author Id* field


## REST

### Genres
	http://localhost:8090/spring-trx-single-db/rest/genre
	
### Author
	http://localhost:8090/spring-trx-single-db/rest/author
	
### Save Author and Genre
	http://localhost:8090/spring-trx-single-db/rest/author-genre	
	
# Sources
		
		https://github.com/paawak/blog/tree/master/code/spring-trx-single-db/spring-transaction/spring-trx-single-db

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
		
	