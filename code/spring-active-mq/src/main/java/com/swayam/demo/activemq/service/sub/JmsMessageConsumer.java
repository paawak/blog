package com.swayam.demo.activemq.service.sub;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JmsMessageConsumer implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(JmsMessageConsumer.class);

    @Override
    public void onMessage(Message message) {

	if (message instanceof TextMessage) {
	    try {
		String textMessage = ((TextMessage) message).getText();
		LOGGER.info("+++++++++++++++++ recieved message: {}", textMessage);
	    } catch (JMSException ex) {
		throw new RuntimeException(ex);
	    }
	} else {
	    throw new IllegalArgumentException("Message must be of type " + TextMessage.class);
	}
    }

}
