package com.swayam.demo.activemq.config;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@ComponentScan("com.swayam.demo.activemq")
@PropertySource("classpath:application.properties")
public class ActiveMQConfig {

    @Autowired
    private Environment environment;

    @Bean(destroyMethod = "stop")
    public ConnectionFactory connectionFactory() {
	ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
	connectionFactory.setBrokerURL(environment.getProperty("jms.brokerUrl"));
	PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
	pooledConnectionFactory.setConnectionFactory(connectionFactory);
	return pooledConnectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
	JmsTemplate jmsTemplate = new JmsTemplate();
	jmsTemplate.setConnectionFactory(connectionFactory);
	return jmsTemplate;
    }

}
