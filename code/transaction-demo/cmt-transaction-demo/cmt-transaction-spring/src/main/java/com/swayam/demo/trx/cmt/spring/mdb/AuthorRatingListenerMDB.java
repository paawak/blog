package com.swayam.demo.trx.cmt.spring.mdb;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swayam.demo.trx.cmt.spring.config.WebappInitializer;
import com.swayam.demo.trx.cmt.spring.service.AuthorRatingService;
import com.swayam.demo.trx.cmt.spring.web.dto.AuthorRatingRequest;

@MessageDriven(name = "HelloWorldQueueMDB",
	activationConfig = { @ActivationConfigProperty(propertyName = "destination", propertyValue = "HELLOWORLDMDBQueue"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
public class AuthorRatingListenerMDB implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorRatingListenerMDB.class);

    private AuthorRatingService authorRatingService;

    @PostConstruct
    @Inject
    public void init(ServletContext servletContext) {

	LOGGER.debug("################## servletContext: {}", servletContext);

	WebApplicationContext applicationContext = (WebApplicationContext) servletContext.getAttribute(WebappInitializer.SPRING_APPLICATION_CONTEXT);

	LOGGER.debug("***************** applicationContext: {}", applicationContext);

	authorRatingService = applicationContext.getBean(AuthorRatingService.class);
    }

    @Override
    public void onMessage(Message message) {

	if (!(message instanceof TextMessage)) {
	    throw new UnsupportedOperationException("Expecting a " + TextMessage.class);
	}

	if (message instanceof TextMessage) {
	    try {
		String textMessage = ((TextMessage) message).getText();
		LOGGER.debug("Text message received: {}", textMessage);
		AuthorRatingRequest authorRatingRequest = new ObjectMapper().readValue(textMessage, AuthorRatingRequest.class);
		LOGGER.debug("authorRatingRequest: {}", authorRatingRequest);
		authorRatingService.addAuthorRating(authorRatingRequest);
	    } catch (JMSException | IOException e) {
		LOGGER.error("exception reading messsage", e);
	    }
	}

    }

}
