package com.swayam.demo.trx.mq;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swayam.demo.trx.dto.AuthorRequest;

@Service
public class JmsMessageConsumer implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(JmsMessageConsumer.class);

    private final ObjectMapper mapper;

    public JmsMessageConsumer(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void onMessage(Message message) {
        AuthorRequest authorRequest;
        try {
            authorRequest = mapper.readValue(((TextMessage) message).getText(), AuthorRequest.class);
        } catch (IOException | JMSException e) {
            throw new RuntimeException(e);
        }

        LOGGER.info("+++++++++++++++++ recieved message: {}", authorRequest);

    }

}
