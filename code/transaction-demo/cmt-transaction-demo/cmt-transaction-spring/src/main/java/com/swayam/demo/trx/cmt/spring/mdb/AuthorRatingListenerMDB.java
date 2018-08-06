package com.swayam.demo.trx.cmt.spring.mdb;

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

import com.swayam.demo.trx.cmt.spring.config.WebappInitializer;
import com.swayam.demo.trx.cmt.spring.dao.AuthorDao;

@MessageDriven(name = "HelloWorldQueueMDB",
	activationConfig = { @ActivationConfigProperty(propertyName = "destination", propertyValue = "HELLOWORLDMDBQueue"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
public class AuthorRatingListenerMDB implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorRatingListenerMDB.class);

    private AuthorDao authorDao;

    @PostConstruct
    @Inject
    public void init(ServletContext servletContext) {

	LOGGER.debug("################## servletContext: {}", servletContext);

	WebApplicationContext applicationContext = (WebApplicationContext) servletContext.getAttribute(WebappInitializer.SPRING_APPLICATION_CONTEXT);

	LOGGER.debug("***************** applicationContext: {}", applicationContext);

	authorDao = applicationContext.getBean(AuthorDao.class);
    }

    @Override
    public void onMessage(Message message) {

	if (!(message instanceof TextMessage)) {
	    throw new UnsupportedOperationException("Expecting a " + TextMessage.class);
	}

	if (message instanceof TextMessage) {
	    TextMessage textMessage = (TextMessage) message;
	    try {
		LOGGER.debug("Text message received: {}", textMessage.getText());
	    } catch (JMSException e) {
		LOGGER.error("exception reading messsage", e);
	    }
	}

    }

}
