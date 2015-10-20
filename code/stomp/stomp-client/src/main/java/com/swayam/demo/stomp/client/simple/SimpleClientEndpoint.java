package com.swayam.demo.stomp.client.simple;

import java.io.IOException;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler.Whole;
import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleClientEndpoint extends Endpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleClientEndpoint.class);

    @Override
    public void onOpen(Session session, EndpointConfig config) {

	LOGGER.info("session opened");

	session.addMessageHandler(String.class, new Whole<String>() {
	    @Override
	    public void onMessage(String text) {
		LOGGER.info("recieved message from server: `{}`", text);
	    }
	});

	String message = "Hello from client";

	LOGGER.info("trying to send message `{}` to server...", message);

	try {
	    session.getBasicRemote().sendText(message);
	    LOGGER.info("message sent successfully");
	} catch (IOException e) {
	    LOGGER.error("error sending message to server", e);
	}

    }

}
