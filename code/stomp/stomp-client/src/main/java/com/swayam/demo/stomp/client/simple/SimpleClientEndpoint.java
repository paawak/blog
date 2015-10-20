package com.swayam.demo.stomp.client.simple;

import java.io.IOException;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleClientEndpoint extends Endpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleClientEndpoint.class);

    @Override
    public void onOpen(Session session, EndpointConfig config) {
	try {

	    LOGGER.info("session opened");

	    session.addMessageHandler(String.class, new MessageHandler.Whole<String>() {
		public void onMessage(String text) {
		    LOGGER.info("recieved message from server: `{}`", text);
		}
	    });

	    String message = "Hello from client";

	    LOGGER.info("sending message to server: `{}`...", message);

	    session.getBasicRemote().sendText(message);

	} catch (IOException ex) {
	    ex.printStackTrace();
	}
    }

}
