package com.swayam.demo.stomp.server.simple;

import javax.websocket.OnMessage;
import javax.websocket.Session;

//@ServerEndpoint(value = "/hello-stomp")
public class SimpleServerEndpoint {

    @OnMessage
    public String onMessage(String message, Session session) {
	return message;
    }

}
