package com.swayam.demo.trx.config;

import javax.jms.ConnectionFactory;
import javax.jms.MessageListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.jms.support.destination.DynamicDestinationResolver;
import org.springframework.transaction.PlatformTransactionManager;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class CommonMQJmsConfig {

    static final String AUTHOR_QUEUE_NAME = "mq.queue.author";

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
        defaultMessageListenerContainer.setDestinationName(environment.getProperty(AUTHOR_QUEUE_NAME));
        // defaultMessageListenerContainer.setSessionAcknowledgeMode(Session.SESSION_TRANSACTED);
        defaultMessageListenerContainer.setMessageListener(messageListener);
        return defaultMessageListenerContainer;
    }

    @Bean
    public JmsOperations jmsTemplate(ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory);
        // jmsTemplate.setSessionAcknowledgeMode(Session.SESSION_TRANSACTED);
        return jmsTemplate;
    }

    @Bean
    public PlatformTransactionManager jmsTxManager(ConnectionFactory connectionFactory) {
        return new JmsTransactionManager(connectionFactory);
    }

    @Bean
    public ObjectMapper mapper() {
        return new ObjectMapper();
    }

}
