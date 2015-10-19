package com.swayam.demo.stomp.client.simple;

import java.io.IOException;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

public class SimpleClientEndpoint extends Endpoint {

    @Override
    public void onOpen(Session session, EndpointConfig config) {
	try {
	    String name = "Duke";
	    System.out.println("Sending message to endpoint: " + name);

	    session.addMessageHandler(String.class,
		    new MessageHandler.Whole<String>() {
			public void onMessage(String text) {
			    System.out
				    .println("SimpleClientEndpoint.onOpen(...).new Whole() {...}.onMessage() text: "
					    + text);
			}
		    });

	    session.getBasicRemote().sendText(name);

	} catch (IOException ex) {
	    ex.printStackTrace();
	}
    }

}
