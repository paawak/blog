package com.swayam.demo.trx.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.swayam.demo.trx.mq.AmqpMessageConsumer;
import com.swayam.demo.trx.mq.AmqpQueuePublisher;
import com.swayam.demo.trx.mq.QueuePublisher;

@Configuration
@PropertySource("classpath:rabbit-mq.properties")
public class RabbitMQAmqpConfig {

    @Autowired
    private Environment environment;

    @Bean
    public MessageListener messageListener() {
        return new AmqpMessageConsumer();
    }

    @Bean
    public QueuePublisher amqpQueuePublisher(AmqpTemplate amqpTemplate) {
        return new AmqpQueuePublisher(environment.getProperty("mq.rabbit.queue.author"), amqpTemplate);
    }

    @Bean
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        com.rabbitmq.client.ConnectionFactory rabbitConnectionFactory = new com.rabbitmq.client.ConnectionFactory();
        rabbitConnectionFactory.setHost(environment.getProperty("mq.rabbit.host"));
        rabbitConnectionFactory.setPort(Integer.parseInt(environment.getProperty("mq.rabbit.port")));
        rabbitConnectionFactory.setUsername(environment.getProperty("mq.rabbit.user"));
        rabbitConnectionFactory.setPassword(environment.getProperty("mq.rabbit.password"));
        return new CachingConnectionFactory(rabbitConnectionFactory);
    }

}
