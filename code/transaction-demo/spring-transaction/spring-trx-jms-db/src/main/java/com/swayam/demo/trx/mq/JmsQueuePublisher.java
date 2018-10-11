package com.swayam.demo.trx.mq;

import javax.jms.Destination;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swayam.demo.trx.dto.AuthorRequest;

@Service
public class JmsQueuePublisher implements QueuePublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(JmsQueuePublisher.class);

    private final ObjectMapper mapper;
    private final Destination authorQueue;
    private final JmsOperations jmsTemplate;

    public JmsQueuePublisher(ObjectMapper mapper, Destination authorQueue, JmsOperations jmsTemplate) {
        this.mapper = mapper;
        this.authorQueue = authorQueue;
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void publish(AuthorRequest authorRequest) {
        LOGGER.info("trying to publish message `{}` to queue...", authorRequest);

        MessageCreator messageCreator = (Session session) -> {
            try {
                return session.createTextMessage(mapper.writeValueAsString(authorRequest));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };

        jmsTemplate.send(authorQueue, messageCreator);
    }

}
