# About

This demoes a simple web-application containing a Message Driven Bean deployed with Wildfly. It uses an externally running Apache ActiveMQ message broker. For details, refer to my blog: <http://palashray.com/mdb-hello-world-with-wildfly-and-activemq-rar-deployed-as-a-module/>

# To post a message to the queue:

	http://localhost:8080/mdb-activemq-module-demo-spring/author.html

# Configuring Wildfly
	
## Creating rar module

### Create directory structure

	mkdir -p WILDFLY_HOME/modules/system/layers/base/org/apache/activemq/ra/main

### Extract the contents of *activemq-rar-5.15.4.rar* into it

	cd WILDFLY_HOME/modules/system/layers/base/org/apache/activemq/ra/main
	cp src/main/wildfly/activemq-rar-5.15.4.rar .
	jar -xvf  activemq-rar-5.15.4.rar 

### Copy the module.xml into it
	
	cp src/main/wildfly/module.xml WILDFLY_HOME/modules/system/layers/base/org/apache/activemq/ra/main/	

## Adding users

- Go to WILDFLY_HOME/bin/add-user
- Create a *Management User*
- Create an *Application User* with name **user**, password **user123**. It should have a role **guest**

## Running Wildfly

	WILDFLY_HOME/bin/standalone.sh -c standalone-with-activemq-module-deployment-spring.xml
	
# Apache ActiveMQ

## Starting ActiveMQ

	bin/activemq start

## ActiveMQ Admin Console
	
	http://localhost:8161/admin/
	
User: admin

Password: admin		

# Further Reading

- <https://developer.jboss.org/wiki/HowToUseOutOfProcessActiveMQWithWildFly>
- <http://www.mastertheboss.com/jboss-frameworks/ironjacamar/configuring-a-resource-adapter-for-activemq-on-jbosswildfly>
- <http://www.mastertheboss.com/jboss-frameworks/ironjacamar/configuring-a-resource-adapter-for-jboss-as7-openmq>
- <https://github.com/wildfly/quickstart/compare/master...jmesnil:helloworld-mdb-activemq-ra>
- <https://github.com/wildfly/quickstart/tree/master/helloworld-mdb>
