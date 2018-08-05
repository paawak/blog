package com.swayam.demo.trx.cmt.spring.web.rest;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swayam.demo.trx.cmt.spring.web.dto.AuthorRatingRequest;

@RestController
@RequestMapping(path = "/rest")
public class AuthorUserRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorUserRestController.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final ApplicationContext applicationContext;
    private final Queue queue;

    public AuthorUserRestController(ApplicationContext applicationContext, Queue queue) {
	this.applicationContext = applicationContext;
	this.queue = queue;
    }

    @PostMapping(path = "/author-rating", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void save(AuthorRatingRequest authorRatingRequest) throws JsonProcessingException, NamingException, JMSException {
	LOGGER.debug("authorRatingRequest: {}", authorRatingRequest);
	postMessageToJMS(objectMapper.writeValueAsString(authorRatingRequest));
    }

    private void postMessageToJMS(String message) throws NamingException, JMSException {
	Session session = applicationContext.getBean(Session.class);
	MessageProducer producer = session.createProducer(queue);
	TextMessage textMessage = session.createTextMessage(message);
	producer.send(textMessage);
	LOGGER.info("sent message: {}", textMessage);
    }

}
