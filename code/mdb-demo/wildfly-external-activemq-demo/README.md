# About

This demoes a simple web-application containing a Message Driven Bean deployed with Wildfly. It uses the embedded Artemis message broker inside of Wildfly.


# To post a message to the queue:

	http://localhost:8080/wildfly-external-activemq-demo/author.html


# Configuring Wildfly

## Running Wildfly
	WILDFLY_HOME/bin/standalone.sh -c standalone-full.xml

## Adding users

- Go to WILDFLY_HOME/bin/add-user
- Create a *Management User*
- Create an *Application User* with name **user**, password **user123**. It should have a role **guest**

## Using external ActiveMQ
- https://developer.jboss.org/wiki/HowToUseOutOfProcessActiveMQWithWildFly

## Creating rar module
### Create directory structure
mkdir -p WILDFLY_HOME/modules/system/layers/base/org/apache/activemq/ra/main

### Extract the contents of *activemq-rar-5.15.4.rar* into it
cd WILDFLY_HOME/modules/system/layers/base/org/apache/activemq/ra/main
jar -xvf  activemq/rar/activemq-rar-5.15.4.rar 

### Copy the module.xml into it
cp src/main/wildfly/module.xml WILDFLY_HOME/modules/system/layers/base/org/apache/activemq/ra/main/
 

### Problems
**java.lang.ClassNotFoundException: org.slf4j.impl.Log4jLoggerAdapter**
**Solution:**
Add the below dependency:
``` xml
		<dependency>
	        <groupId>org.slf4j</groupId>
	        <artifactId>slf4j-log4j12</artifactId>
	        <version>${slf4j.version}</version>
	    </dependency>
```

**java.lang.ClassNotFoundException: org.slf4j.impl.Slf4jLogger**
**Solution:**
Add the below dependency:
``` xml
		<dependency>
		    <groupId>org.jboss.slf4j</groupId>
		    <artifactId>slf4j-jboss-logmanager</artifactId>
		    <version>1.0.4.GA</version>
		</dependency>
```
 
** java.lang.NoClassDefFoundError: org/jboss/logmanager/Level**
**Solution:**
``` xml
		<dependency>
			<groupId>org.jboss.logmanager</groupId>
		    <artifactId>jboss-logmanager</artifactId>
			<version>2.1.4.Final</version>
		</dependency>
```	

**java.lang.ClassCastException: Cannot cast org.jboss.logmanager.Logger to org.jboss.logmanager.Logger**
**Solution:**
In the below subsystem,
``` xml
<subsystem xmlns="urn:jboss:domain:logging:5.0">
```

Put the below lines

``` xml
            <add-logging-api-dependencies value="false"/>
            <use-deployment-logging-config value="true"/>
```

## Using the embedded message broker ActiveMQ Artemis


### Create Queue

Under the

``` xml
			<subsystem xmlns="urn:jboss:domain:messaging-activemq:3.0">
```

add the below lines

``` xml
			<jms-queue name="myTestQueue" entries="jms/queue/mytest java:jboss/exported/jms/queue/mytest"/>
			<jms-topic name="myTestTopic" entries="jms/topic/mytest java:jboss/exported/jms/topic/mytest"/>
```
	


	