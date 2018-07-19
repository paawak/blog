package com.swayam.demo.mdb.config;

import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

@PropertySource("classpath:mdb.properties")
@Configuration
public class MdbConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(MdbConfig.class);

    @Autowired
    private Environment environment;

    @Bean
    public InitialContext context() throws NamingException {
	Hashtable<String, String> env = new Hashtable<String, String>();
	env.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
	env.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
	env.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
	env.put("jboss.naming.client.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT", "false");
	env.put(Context.SECURITY_PRINCIPAL, environment.getProperty("WILDFLY_USER"));
	env.put(Context.SECURITY_CREDENTIALS, environment.getProperty("WILDFLY_PASSWORD"));
	InitialContext context = new InitialContext(env);
	LOGGER.info("got the initial-context");
	return context;
    }

    @Bean
    public QueueConnectionFactory queueConnectionFactory(InitialContext context) throws NamingException {
	QueueConnectionFactory factory = (QueueConnectionFactory) context.lookup(environment.getProperty("ARTEMIS_JMS_CONNECTION_FACTORY"));
	LOGGER.info("got the connection factory");
	return factory;
    }

    @Bean
    public Queue jmsQueue(InitialContext context) throws NamingException {
	Queue queue = (Queue) context.lookup(environment.getProperty("ARTEMIS_QUEUE_LOOKUP"));
	LOGGER.info("got the queue");
	return queue;
    }

    @Bean(destroyMethod = "close")
    @Scope("prototype")
    public QueueConnection queueConnection(QueueConnectionFactory queueConnectionFactory) throws JMSException {
	QueueConnection queueConnection = queueConnectionFactory.createQueueConnection(environment.getProperty("WILDFLY_USER"), environment.getProperty("WILDFLY_PASSWORD"));
	LOGGER.info("created a connection");
	return queueConnection;
    }

    @Bean(destroyMethod = "close")
    @Scope("prototype")
    public QueueSession queueSession(QueueConnection queueConnection) throws JMSException {
	QueueSession queueSession = queueConnection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
	LOGGER.info("created a queueSession");
	return queueSession;

    }

}
