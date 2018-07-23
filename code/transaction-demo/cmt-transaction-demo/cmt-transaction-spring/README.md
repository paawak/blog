# About

This demoes a simple web-application, and how transaction works

# How to build

The below command cleans and initializes the Postgres and Mysql schema with Flyway and builds the war

	mvn clean intsall -P db-migration

# Accessing the API

## UI

### book.html (http://localhost:8080/cmt-transaction-spring/book.html)
- Saves data in 2 different tables within the same Postgres schema: **genre** and **author**
- The table **genre** has an auto-increment primary key. 
- The table **author** expects an unique primary key id every time from the user
- The **author** table already contains data with Primary Keys ranging from *1* to *10*
- To simulate transaction failure (unique constraint violation), the user can enter values from *1* to *10* in the *Author Id* field
- For successful insertion the user should enter values above *10* in the *Author Id* field

### rating.html (http://localhost:8080/cmt-transaction-spring/rating.html)
- Saves data in 2 different databases: **author** table in **Postgres** and **rating** table in **Mysql**	
- The table **rating** has an auto-increment primary key. 
- The table **author** expects an unique primary key id every time from the user
- The **author** table already contains data with Primary Keys ranging from *1* to *10*
- To simulate transaction failure (unique constraint violation), the user can enter values from *1* to *10* in the *Author Id* field
- For successful insertion the user should enter values above *10* in the *Author Id* field

## REST

### Genres
	http://localhost:8080/cmt-transaction-spring/rest/genre
	
### Author
	http://localhost:8080/cmt-transaction-spring/rest/author
	
### Save Author and Genre
	http://localhost:8080/cmt-transaction-spring/rest/author-genre	

### Save Author and Rating	
	http://localhost:8080/cmt-transaction-spring/rest/author-rating
	
# Sources
		
		https://github.com/paawak/blog/tree/master/code/transaction-demo
	
# Running on Wildfly 13

## Configuring Users

To add a new user execute the add-user.sh script within the bin folder of your WildFly installation and enter the requested information.

The ManagementRealm user is manager/manager123

## Configuring JDBC Driver

To install a JDBC driver as a module you need to create a file path structure under the WILDFLY_HOME/modules, copy the JDBC driver JAR into the main/ subdirectory and create a module.xml file. The contents of module.xml is below:

``` xml
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
		
	