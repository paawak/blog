package com.swayam.demo.activemq.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swayam.demo.activemq.model.BankDetail;

@Service
public class JmsQueuePublisher implements QueuePublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(JmsQueuePublisher.class);

    private final ObjectMapper mapper = new ObjectMapper();
    private final JmsTemplate jmsTemplate;

    @Autowired
    public JmsQueuePublisher(JmsTemplate jmsTemplate) {
	this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void publish(BankDetail bankDetail) {
	String jsonString;
	try {
	    jsonString = mapper.writeValueAsString(bankDetail);
	} catch (JsonProcessingException e) {
	    throw new RuntimeException(e);
	}

	LOGGER.info("trying to publish message `{}` to queue...", jsonString);

	jmsTemplate.convertAndSend("bank-details", jsonString);

    }

}
