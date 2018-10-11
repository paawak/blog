package com.swayam.demo.trx.config;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.rabbitmq.jms.admin.RMQConnectionFactory;
import com.rabbitmq.jms.admin.RMQDestination;

@Profile("rabbitmq")
@Configuration
@PropertySource("classpath:rabbit-mq.properties")
public class RabbitMQJmsConfig {

    @Autowired
    private Environment environment;

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
    public Destination authorQueue() {
        return new RMQDestination(environment.getProperty(CommonMQJmsConfig.AUTHOR_QUEUE_NAME), true, false);
    }

}
