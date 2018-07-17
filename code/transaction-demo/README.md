# About

This demoes a simple web-application, and how transaction works

# How to build

The below command cleans and initializes the Postgres and Mysql schema with Flyway and builds the war

	mvn clean intsall -P db-migration

# How to run with Jetty

	 mvn jetty:run -Dspring.profiles.active=local

# Accessing the API

## UI

### book.html (http://localhost:8090/transaction-demo/book.html)
- Saves data in 2 different tables within the same Postgres schema: **genre** and **author**
- The table **genre** has an auto-increment primary key. 
- The table **author** expects an unique primary key id every time from the user
- The **author** table already contains data with Primary Keys ranging from *1* to *10*
- To simulate transaction failure (unique constraint violation), the user can enter values from *1* to *10* in the *Author Id* field
- For successful insertion the user should enter values above *10* in the *Author Id* field

### rating.html (http://localhost:8090/transaction-demo/rating.html)
- Saves data in 2 different databases: **author** table in **Postgres** and **rating** table in **Mysql**	
- The table **rating** has an auto-increment primary key. 
- The table **author** expects an unique primary key id every time from the user
- The **author** table already contains data with Primary Keys ranging from *1* to *10*
- To simulate transaction failure (unique constraint violation), the user can enter values from *1* to *10* in the *Author Id* field
- For successful insertion the user should enter values above *10* in the *Author Id* field

## REST

### Genres
	http://localhost:8090/transaction-demo/rest/genre
	
### Author
	http://localhost:8090/transaction-demo/rest/author
	
### Save Author and Genre
	http://localhost:8090/transaction-demo/rest/author-genre	

### Save Author and Rating	
	http://localhost:8090/transaction-demo/rest/author-rating
	
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

## Integrating ActiveMQ
- Download ActiveMQ RAR from: http://repo1.maven.org/maven2/org/apache/activemq/activemq-rar/
- http://activemq.apache.org/jboss-integration.html
- http://www.mastertheboss.com/jboss-server/jboss-jms/integrate-activemq-with-wildfly

Under the standalone.xml

``` xml
        <subsystem xmlns="urn:jboss:domain:resource-adapters:5.0">
            <resource-adapters>
                <resource-adapter id="activemq">
                    <archive>
                        activemq-rar-5.15.4.rar
                    </archive>
         
                    <transaction-support>XATransaction</transaction-support>
         
                    <config-property name="UseInboundSession">
                        false
                    </config-property>
         
                    <config-property name="Password">
                        defaultPassword
                    </config-property>
         
                    <config-property name="UserName">
                        defaultUser
                    </config-property>
         
                    <config-property name="ServerUrl">
                        tcp://localhost:61616
                    </config-property>
         
                    <connection-definitions>
                        <connection-definition class-name="org.apache.activemq.ra.ActiveMQManagedConnectionFactory" jndi-name="java:/ConnectionFactory" enabled="true" pool-name="ConnectionFactory">
         
                            <xa-pool>
                                <min-pool-size>1</min-pool-size>
                                <max-pool-size>20</max-pool-size>
                                <prefill>false</prefill>
                                <is-same-rm-override>false</is-same-rm-override>
                            </xa-pool>
         
                        </connection-definition>
                    </connection-definitions>
         
                    <admin-objects>
                        <admin-object class-name="org.apache.activemq.command.ActiveMQQueue" jndi-name="java:jboss/activemq/queue/PaawakTestQueue" use-java-context="true" pool-name="TestQueue">
         
                            <config-property name="PhysicalName">
                                activemq/queue/PaawakTestQueue
                            </config-property>
         
                        </admin-object>
         
                        <admin-object class-name="org.apache.activemq.command.ActiveMQTopic" jndi-name="java:jboss/activemq/topic/PaawakTestTopic" use-java-context="true" pool-name="PaawakTestTopic">
         
                            <config-property name="PhysicalName">
                                activemq/topic/PaawakTestTopic
                            </config-property>
         
                        </admin-object>
                    </admin-objects>
                </resource-adapter>
            </resource-adapters>
        </subsystem>
```	

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
		
	