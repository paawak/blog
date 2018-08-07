package com.swayam.demo.mdb.artemis.config;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    @Bean
    public JndiTemplate jndiTemplate() {
	return new JndiTemplate();
    }

    @Bean
    public QueueConnectionFactory queueConnectionFactory(JndiTemplate jndiTemplate) throws NamingException {
	QueueConnectionFactory factory = jndiTemplate.lookup(environment.getProperty("ARTEMIS_JMS_CONNECTION_FACTORY"), QueueConnectionFactory.class);
	LOGGER.info("got the connection factory");
	return factory;
    }

    @Bean
    public Queue jmsQueue(JndiTemplate jndiTemplate) throws NamingException {
	Queue queue = jndiTemplate.lookup(environment.getProperty("ARTEMIS_QUEUE_LOOKUP"), Queue.class);
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
