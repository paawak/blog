package com.swayam.demo.trx.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swayam.demo.trx.dto.AuthorRequest;

public class AmqpQueuePublisher implements QueuePublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(AmqpQueuePublisher.class);

    private final ObjectMapper mapper = new ObjectMapper();
    private final String queueName;
    private final AmqpTemplate amqpTemplate;

    public AmqpQueuePublisher(String queueName, AmqpTemplate amqpTemplate) {
        this.queueName = queueName;
        this.amqpTemplate = amqpTemplate;
    }

    @Override
    public void publish(AuthorRequest authorRequest) {
        String jsonString;
        try {
            jsonString = mapper.writeValueAsString(authorRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        LOGGER.info("trying to publish message `{}` to queue...", jsonString);

        amqpTemplate.convertAndSend(queueName, jsonString);

    }

}
