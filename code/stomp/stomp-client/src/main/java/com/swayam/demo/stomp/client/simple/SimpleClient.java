package com.swayam.demo.stomp.client.simple;

import java.io.IOException;
import java.net.URI;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;

public class SimpleClient {

    public static void main(String[] args) throws DeploymentException,
	    IOException, InterruptedException {
	WebSocketContainer container = ContainerProvider
		.getWebSocketContainer();
	String uri = "ws://localhost:8080/stomp-server/hello-stomp";
	container.connectToServer(SimpleClientEndpoint.class, URI.create(uri));

	Thread.sleep(10_000);

    }

}
