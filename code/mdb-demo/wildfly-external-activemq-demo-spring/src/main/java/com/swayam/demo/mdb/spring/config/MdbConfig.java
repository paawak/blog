package com.swayam.demo.mdb.spring.config;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.jndi.JndiTemplate;

@PropertySource("classpath:mdb.properties")
@Configuration
public class MdbConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(MdbConfig.class);

	@Autowired
	private Environment environment;

	@Lazy
	@Bean
	public JndiTemplate jndiTemplate() throws NamingException {
		Properties env = new Properties();
		env.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		env.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		env.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
		env.put("jboss.naming.client.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT", "false");
		env.put(Context.SECURITY_PRINCIPAL, environment.getProperty("WILDFLY_USER"));
		env.put(Context.SECURITY_CREDENTIALS, environment.getProperty("WILDFLY_PASSWORD"));
		JndiTemplate jndiTemplate = new JndiTemplate(env);
		LOGGER.info("################################ got the initial-context");
		return jndiTemplate;
	}

	@Lazy
	@Bean
	public ConnectionFactory jmsConnectionFactory(JndiTemplate jndiTemplate) throws NamingException {
		LOGGER.info("******************** looking for the jms connection factory");
		ConnectionFactory factory = jndiTemplate.lookup(environment.getProperty("ACTIVEMQ_JMS_CONNECTION_FACTORY"),
				ConnectionFactory.class);
		LOGGER.info("################################ got the jms connection factory");
		return factory;
	}

	@Lazy
	@Bean
	public Queue jmsQueue(JndiTemplate jndiTemplate) throws NamingException {
		Queue queue = jndiTemplate.lookup(environment.getProperty("ACTIVEMQ_QUEUE_LOOKUP"), Queue.class);
		LOGGER.info("################################ got the queue");
		return queue;
	}

	@Lazy
	@Bean(destroyMethod = "close")
	@Scope("prototype")
	public Connection jmsConnection(ConnectionFactory jmsConnectionFactory) throws JMSException {
		Connection jmsConnection = jmsConnectionFactory.createConnection(environment.getProperty("WILDFLY_USER"),
				environment.getProperty("WILDFLY_PASSWORD"));
		LOGGER.info("################################ created a jmsConnection");
		return jmsConnection;
	}

	@Lazy
	@Bean(destroyMethod = "close")
	@Scope("prototype")
	public Session jmsSession(Connection jmsConnection) throws JMSException {
		Session jmsSession = jmsConnection.createSession(true, QueueSession.AUTO_ACKNOWLEDGE);
		LOGGER.info("################################ created a jmsSession");
		return jmsSession;

	}

}
