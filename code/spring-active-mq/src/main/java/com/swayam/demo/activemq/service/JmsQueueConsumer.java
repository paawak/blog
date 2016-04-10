package com.swayam.demo.activemq.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class JmsQueueConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JmsQueueConsumer.class);

    @JmsListener(destination = "bank-details")
    public void processBankDetails(String data) {
	LOGGER.info("recieved message: {}", data);
    }

}
