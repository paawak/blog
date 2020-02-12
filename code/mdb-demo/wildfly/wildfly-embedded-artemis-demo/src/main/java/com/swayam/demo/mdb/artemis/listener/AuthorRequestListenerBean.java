package com.swayam.demo.mdb.artemis.listener;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MessageDriven(activationConfig = {
	@ActivationConfigProperty(propertyName = "destinationType",
		propertyValue = "javax.jms.Queue"),
	@ActivationConfigProperty(propertyName = "destination",
		propertyValue = "queue/HELLOWORLDMDBQueue") })
public class AuthorRequestListenerBean implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorRequestListenerBean.class);

    @Override
    public void onMessage(Message message) {

	if (!(message instanceof TextMessage)) {
	    throw new UnsupportedOperationException("Expecting a " + TextMessage.class);
	}

	if (message instanceof TextMessage) {
	    TextMessage textMessage = (TextMessage) message;
	    try {
		LOGGER.info("Text message received: {}", textMessage.getText());
	    } catch (JMSException e) {
		LOGGER.error("exception reading messsage", e);
	    }
	}

    }

}
