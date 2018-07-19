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

## Using external ActiveMW
- https://developer.jboss.org/wiki/HowToUseOutOfProcessActiveMQWithWildFly

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
	


	