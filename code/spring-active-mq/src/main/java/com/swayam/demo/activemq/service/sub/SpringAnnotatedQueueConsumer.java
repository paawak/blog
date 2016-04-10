package com.swayam.demo.activemq.service.sub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class SpringAnnotatedQueueConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringAnnotatedQueueConsumer.class);

    @JmsListener(destination = "bank-details")
    public void processBankDetails(String data) {
	LOGGER.info("--------------------------- recieved message: {}", data);
    }

}
