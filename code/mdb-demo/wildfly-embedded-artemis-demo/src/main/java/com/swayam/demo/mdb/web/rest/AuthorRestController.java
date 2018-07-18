package com.swayam.demo.mdb.web.rest;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
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
import com.swayam.demo.mdb.web.dto.AuthorRequest;

@RestController
@RequestMapping(path = "/rest")
public class AuthorRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorRestController.class);

	private final ObjectMapper objectMapper = new ObjectMapper();

	private final ApplicationContext applicationContext;
	private final Queue queue;

	public AuthorRestController(ApplicationContext applicationContext, Queue queue) {
		this.applicationContext = applicationContext;
		this.queue = queue;
	}

	@PostMapping(path = "/author", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String save(AuthorRequest authorRequest) throws JsonProcessingException, NamingException, JMSException {
		LOGGER.debug("authorRequest: {}", authorRequest);
		postMessageToJMS(objectMapper.writeValueAsString(authorRequest));
		return "success";
	}

	private void postMessageToJMS(String message) throws NamingException, JMSException {
		QueueSession session = applicationContext.getBean(QueueSession.class);
		QueueSender sender = session.createSender(queue);
		TextMessage textMessage = session.createTextMessage(message);
		sender.send(textMessage);
		LOGGER.info("sent message: {}", textMessage);
	}

}
