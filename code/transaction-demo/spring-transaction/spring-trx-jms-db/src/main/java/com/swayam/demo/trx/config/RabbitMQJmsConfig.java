package com.swayam.demo.trx.config;

import javax.jms.ConnectionFactory;
import javax.jms.MessageListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.jms.support.destination.DynamicDestinationResolver;
import org.springframework.transaction.PlatformTransactionManager;

import com.rabbitmq.jms.admin.RMQConnectionFactory;
import com.swayam.demo.trx.mq.JmsMessageConsumer;
import com.swayam.demo.trx.mq.JmsQueuePublisher;
import com.swayam.demo.trx.mq.QueuePublisher;

@Configuration
@PropertySource("classpath:rabbit-mq.properties")
public class RabbitMQJmsConfig {

    @Autowired
    private Environment environment;

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory, DestinationResolver destinationResolver) {
	DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
	factory.setConnectionFactory(connectionFactory);
	factory.setDestinationResolver(destinationResolver);
	factory.setConcurrency("3-10");
	return factory;
    }

    @Bean
    public DestinationResolver destinationResolver() {
	return new DynamicDestinationResolver();
    }

    @Bean
    public MessageListenerContainer defaultMessageListenerContainer(ConnectionFactory connectionFactory, MessageListener messageListener) {
	DefaultMessageListenerContainer defaultMessageListenerContainer = new DefaultMessageListenerContainer();
	defaultMessageListenerContainer.setConnectionFactory(connectionFactory);
	defaultMessageListenerContainer.setDestinationName(environment.getProperty("mq.rabbit.queue.author"));
	defaultMessageListenerContainer.setMessageListener(messageListener);
	return defaultMessageListenerContainer;
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
	JmsTemplate jmsTemplate = new JmsTemplate();
	jmsTemplate.setConnectionFactory(connectionFactory);
	return jmsTemplate;
    }

    @Bean
    public MessageListener messageListener() {
	return new JmsMessageConsumer();
    }

    @Bean
    public QueuePublisher jmsQueuePublisher(JmsTemplate jmsTemplate) {
	return new JmsQueuePublisher(environment.getProperty("mq.rabbit.queue.author"), jmsTemplate);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
	RMQConnectionFactory connectionFactory = new RMQConnectionFactory();
	connectionFactory.setHost(environment.getProperty("mq.rabbit.host"));
	connectionFactory.setPort(Integer.parseInt(environment.getProperty("mq.rabbit.port")));
	connectionFactory.setUsername(environment.getProperty("mq.rabbit.username"));
	connectionFactory.setPassword(environment.getProperty("mq.rabbit.password"));
	return connectionFactory;
    }

    @Bean
    public PlatformTransactionManager jmsTxManager(ConnectionFactory connectionFactory) {
	return new JmsTransactionManager(connectionFactory);
    }

}
