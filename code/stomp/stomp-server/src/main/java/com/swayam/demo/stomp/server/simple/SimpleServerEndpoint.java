package com.swayam.demo.stomp.server.simple;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/hello-stomp")
public class SimpleServerEndpoint {

    @OnMessage
    public String onMessage(String message, Session session) {
	System.out.println("SimpleServerEndpoint.onMessage() message: "
		+ message);
	return "[" + System.currentTimeMillis() + "]" + message;
    }

}
