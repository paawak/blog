package com.swayam.demo.stomp.client.simple;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.DeploymentException;

import org.glassfish.tyrus.client.ClientManager;

public class SimpleClient {

    public static void main(String[] args) throws InterruptedException,
	    URISyntaxException, DeploymentException, IOException {

	ClientEndpointConfig cec = ClientEndpointConfig.Builder.create()
		.build();

	ClientManager client = ClientManager.createClient();
	client.connectToServer(SimpleClientEndpoint.class, cec, new URI(
		"ws://localhost:8080/stomp-server/hello-stomp"));

	Thread.sleep(10_000);

    }

}
