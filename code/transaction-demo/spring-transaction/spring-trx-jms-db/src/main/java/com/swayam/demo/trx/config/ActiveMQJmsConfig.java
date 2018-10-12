package com.swayam.demo.trx.config;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@Profile("activemq")
@PropertySource("classpath:active-mq.properties")
public class ActiveMQJmsConfig {

    @Autowired
    private Environment environment;

    @Bean(destroyMethod = "stop")
    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(environment.getProperty("mq.active.url"));
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        redeliveryPolicy.setRedeliveryDelay(3_000);
        redeliveryPolicy.setMaximumRedeliveries(3);
        connectionFactory.setRedeliveryPolicy(redeliveryPolicy);
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
        pooledConnectionFactory.setConnectionFactory(connectionFactory);
        return pooledConnectionFactory;
    }

    @Bean
    public Destination authorQueue() {
        return new ActiveMQQueue(environment.getProperty(CommonMQJmsConfig.AUTHOR_QUEUE_NAME));
    }

}
