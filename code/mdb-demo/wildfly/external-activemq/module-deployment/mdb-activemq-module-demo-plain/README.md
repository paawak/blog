# About

This demoes a simple web-application containing a Message Driven Bean deployed with Wildfly. It uses an externally running Apache ActiveMQ message broker. For details, refer to my blog: <http://palashray.com/mdb-hello-world-using-jee-cdi-on-wildfly-with-activemq-rar-deployed-as-a-module/>.

# To post a message to the queue:

	http://localhost:8080/mdb-activemq-module-demo-plain/author.jsp

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

		WILDFLY_HOME/bin/standalone.sh -c standalone-with-activemq-module-deployment-plain.xml
	
# Apache ActiveMQ

## Starting ActiveMQ

	bin/activemq start

## ActiveMQ Admin Console
	
	http://localhost:8161/admin/
	
User: admin

Password: admin		

# Common Problems And Their Solutions

## java.lang.ClassNotFoundException: org.slf4j.impl.Log4jLoggerAdapter

Add the below dependency:

``` xml
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
            <version>${slf4j.version}</version>
        </dependency>
```

## java.lang.ClassNotFoundException: org.slf4j.impl.Slf4jLogger

Add the below dependency:

``` xml
        <dependency>
            <groupId>org.jboss.slf4j</groupId>
            <artifactId>slf4j-jboss-logmanager</artifactId>
            <version>1.0.4.GA</version>
        </dependency>
```
 
## java.lang.NoClassDefFoundError: org/jboss/logmanager/Level

``` xml
        <dependency>
            <groupId>org.jboss.logmanager</groupId>
            <artifactId>jboss-logmanager</artifactId>
            <version>2.1.4.Final</version>
        </dependency>
```

## java.lang.ClassCastException: Cannot cast org.jboss.logmanager.Logger to org.jboss.logmanager.Logger

In the below subsystem,

``` xml
<subsystem xmlns="urn:jboss:domain:logging:5.0">
```

Put the below lines

``` xml
            <add-logging-api-dependencies value="false"/>
            <use-deployment-logging-config value="true"/>
```

## java.lang.NullPointerException: "WFLYCTL0412: Required services that are not installed

In the class *HelloWorldMDBServletClient*, remove the *@JMSDestinationDefinitions* annotation below:

``` java
@JMSDestinationDefinitions(value = { @JMSDestinationDefinition(name = HelloWorldMDBServletClient.QUEUE_JNDI_NAME, interfaceName = "javax.jms.Queue", destinationName = "HELLOWORLDMDBQueue") })
@WebServlet("/rest/author")
public class HelloWorldMDBServletClient extends HttpServlet {
```

It should look like:

``` java stack-trace
@WebServlet("/rest/author")
public class HelloWorldMDBServletClient extends HttpServlet {
```

The Queue should be defined in the *standalone.xml* instead

### Stack Trace Details

``` java
07:45:40,398 ERROR [org.jboss.msc.service.fail] (MSC service thread 1-3) MSC000001: Failed to start service jboss.deployment.unit."mdb-activemq-module-demo-plain.war".INSTALL: org.jboss.msc.service.StartException in service jboss.deployment.unit."mdb-activemq-module-demo-plain.war".INSTALL: WFLYSRV0153: Failed to process phase INSTALL of deployment "mdb-activemq-module-demo-plain.war"
	at org.jboss.as.server@10.0.3.Final//org.jboss.as.server.deployment.DeploymentUnitPhaseService.start(DeploymentUnitPhaseService.java:183)
	at org.jboss.msc@1.4.11.Final//org.jboss.msc.service.ServiceControllerImpl$StartTask.startService(ServiceControllerImpl.java:1739)
	at org.jboss.msc@1.4.11.Final//org.jboss.msc.service.ServiceControllerImpl$StartTask.execute(ServiceControllerImpl.java:1701)
	at org.jboss.msc@1.4.11.Final//org.jboss.msc.service.ServiceControllerImpl$ControllerTask.run(ServiceControllerImpl.java:1559)
	at org.jboss.threads@2.3.3.Final//org.jboss.threads.ContextClassLoaderSavingRunnable.run(ContextClassLoaderSavingRunnable.java:35)
	at org.jboss.threads@2.3.3.Final//org.jboss.threads.EnhancedQueueExecutor.safeRun(EnhancedQueueExecutor.java:1982)
	at org.jboss.threads@2.3.3.Final//org.jboss.threads.EnhancedQueueExecutor$ThreadBody.doRunTask(EnhancedQueueExecutor.java:1486)
	at org.jboss.threads@2.3.3.Final//org.jboss.threads.EnhancedQueueExecutor$ThreadBody.run(EnhancedQueueExecutor.java:1377)
	at java.base/java.lang.Thread.run(Thread.java:834)
Caused by: java.lang.NullPointerException
	at org.jboss.as.naming@18.0.1.Final//org.jboss.as.naming.deployment.ContextNames.bindInfoFor(ContextNames.java:351)
	at org.wildfly.extension.messaging-activemq//org.wildfly.extension.messaging.activemq.deployment.JMSConnectionFactoryDefinitionInjectionSource.getDefaulResourceAdapter(JMSConnectionFactoryDefinitionInjectionSource.java:416)
	at org.wildfly.extension.messaging-activemq//org.wildfly.extension.messaging.activemq.deployment.JMSDestinationDefinitionInjectionSource.getResourceValue(JMSDestinationDefinitionInjectionSource.java:137)
	at org.jboss.as.ee@18.0.1.Final//org.jboss.as.ee.component.deployers.ModuleJndiBindingProcessor.addJndiBinding(ModuleJndiBindingProcessor.java:289)
	at org.jboss.as.ee@18.0.1.Final//org.jboss.as.ee.component.deployers.ModuleJndiBindingProcessor$1.handle(ModuleJndiBindingProcessor.java:240)
	at org.jboss.as.ee@18.0.1.Final//org.jboss.as.ee.component.ClassDescriptionTraversal.run(ClassDescriptionTraversal.java:54)
	at org.jboss.as.ee@18.0.1.Final//org.jboss.as.ee.component.deployers.ModuleJndiBindingProcessor.processClassConfigurations(ModuleJndiBindingProcessor.java:244)
	at org.jboss.as.ee@18.0.1.Final//org.jboss.as.ee.component.deployers.ModuleJndiBindingProcessor.deploy(ModuleJndiBindingProcessor.java:158)
	at org.jboss.as.server@10.0.3.Final//org.jboss.as.server.deployment.DeploymentUnitPhaseService.start(DeploymentUnitPhaseService.java:176)
	... 8 more

07:45:40,418 INFO  [org.infinispan.factories.GlobalComponentRegistry] (MSC service thread 1-1) ISPN000128: Infinispan version: Infinispan 'Infinity Minus ONE +2' 9.4.16.Final
07:45:40,708 INFO  [org.jboss.as.clustering.infinispan] (ServerService Thread Pool -- 80) WFLYCLINF0002: Started client-mappings cache from ejb container
07:45:40,761 ERROR [org.jboss.as.controller.management-operation] (Controller Boot Thread) WFLYCTL0013: Operation ("deploy") failed - address: ([("deployment" => "mdb-activemq-module-demo-plain.war")]) - failure description: {
    "WFLYCTL0080: Failed services" => {"jboss.deployment.unit.\"mdb-activemq-module-demo-plain.war\".INSTALL" => "WFLYSRV0153: Failed to process phase INSTALL of deployment \"mdb-activemq-module-demo-plain.war\"
    Caused by: java.lang.NullPointerException"},
    "WFLYCTL0412: Required services that are not installed:" => [
        "jboss.deployment.unit.\"mdb-activemq-module-demo-plain.war\".WeldStartService",
        "jboss.deployment.unit.\"mdb-activemq-module-demo-plain.war\".beanmanager"
    ],
    "WFLYCTL0180: Services with missing/unavailable dependencies" => [
        "jboss.deployment.unit.\"mdb-activemq-module-demo-plain.war\".batch.artifact.factory is missing [jboss.deployment.unit.\"mdb-activemq-module-demo-plain.war\".beanmanager]",
        "jboss.deployment.unit.\"mdb-activemq-module-demo-plain.war\".weld.weldClassIntrospector is missing [jboss.deployment.unit.\"mdb-activemq-module-demo-plain.war\".beanmanager, jboss.deployment.unit.\"mdb-activemq-module-demo-plain.war\".WeldStartService]"
    ]
}
07:45:40,839 INFO  [org.jboss.as.server] (ServerService Thread Pool -- 45) WFLYSRV0010: Deployed "mdb-activemq-module-demo-plain.war" (runtime-name : "mdb-activemq-module-demo-plain.war")
07:45:40,854 INFO  [org.jboss.as.controller] (Controller Boot Thread) WFLYCTL0183: Service status report
WFLYCTL0184:    New missing/unsatisfied dependencies:
      service jboss.deployment.unit."mdb-activemq-module-demo-plain.war".WeldStartService (missing) dependents: [service jboss.deployment.unit."mdb-activemq-module-demo-plain.war".weld.weldClassIntrospector] 
      service jboss.deployment.unit."mdb-activemq-module-demo-plain.war".beanmanager (missing) dependents: [service jboss.deployment.unit."mdb-activemq-module-demo-plain.war".weld.weldClassIntrospector, service jboss.deployment.unit."mdb-activemq-module-demo-plain.war".batch.artifact.factory] 
WFLYCTL0186:   Services which failed to start:      service jboss.deployment.unit."mdb-activemq-module-demo-plain.war".INSTALL: WFLYSRV0153: Failed to process phase INSTALL of deployment "mdb-activemq-module-demo-plain.war"
WFLYCTL0448: 2 additional services are down due to their dependencies being missing or failed
```

# Further Reading

- <https://developer.jboss.org/wiki/HowToUseOutOfProcessActiveMQWithWildFly>
- <http://www.mastertheboss.com/jboss-frameworks/ironjacamar/configuring-a-resource-adapter-for-activemq-on-jbosswildfly>
- <http://www.mastertheboss.com/jboss-frameworks/ironjacamar/configuring-a-resource-adapter-for-jboss-as7-openmq>
- <https://github.com/wildfly/quickstart/compare/master...jmesnil:helloworld-mdb-activemq-ra>
- <https://github.com/wildfly/quickstart/tree/master/helloworld-mdb>
