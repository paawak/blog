# About

This demoes a simple web-application containing a Message Driven Bean deployed with Wildfly. It uses the embedded Artemis message broker inside of Wildfly. You can read about this in my blog: <http://palashray.com/mdb-hello-world-with-wildfly-and-activemq-with-rar-deployment/>


# To post a message to the queue:

	http://localhost:8080/mdb-activemq-rar-demo-spring/author.html


# Configuring Wildfly
## Deploying ActiveMQ RAR as archive

Simply copy the *src/main/wildfly/activemq-rar-5.15.4.rar* into the *standalone/deployments* directory

## Adding users

- Go to WILDFLY_HOME/bin/add-user
- Create a *Management User*
- Create an *Application User* with name **user**, password **user123**. It should have a role **guest**


## Running Wildfly

	WILDFLY_HOME/bin/standalone.sh -c standalone-with-external-activemq-rar-deployment.xml

# Apache ActiveMQ

## Starting ActiveMQ

	bin/activemq start

## ActiveMQ Admin Console
	
	http://localhost:8161/admin/
	
User: admin

Password: admin	

# Further Reading
- <https://docs.oracle.com/javaee/6/tutorial/doc/gipgl.html>
- <https://developer.jboss.org/wiki/HowToUseOutOfProcessActiveMQWithWildFly>
- <http://www.mastertheboss.com/jboss-frameworks/ironjacamar/configuring-a-resource-adapter-for-activemq-on-jbosswildfly>
- <http://www.mastertheboss.com/jboss-frameworks/ironjacamar/configuring-a-resource-adapter-for-jboss-as7-openmq>
- <https://github.com/wildfly/quickstart/compare/master...jmesnil:helloworld-mdb-activemq-ra>
- <https://github.com/wildfly/quickstart/tree/master/helloworld-mdb>

