package com.swayam.demo.stomp.server.simple;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ServerEndpoint(value = "/hello-stomp")
public class SimpleServerEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleServerEndpoint.class);

    private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";

    @OnMessage
    public String onMessage(String message, Session session) {
	LOGGER.info("Message from client: `{}`", message);
	String serverMessage = new StringBuilder(100).append("Message processed by SimpleServer at [").append(new SimpleDateFormat(DATE_FORMAT).format(new Date())).append("]").append(message)
		.toString();
	return serverMessage;
    }
}
