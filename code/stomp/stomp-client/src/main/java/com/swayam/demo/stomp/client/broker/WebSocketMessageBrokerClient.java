package com.swayam.demo.stomp.client.broker;

import java.io.IOException;

import javax.websocket.DeploymentException;

import org.glassfish.tyrus.client.ClientManager;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

public class WebSocketMessageBrokerClient {

    public static void main(String[] args) throws DeploymentException,
	    IOException {
	ClientManager client = ClientManager.createClient();
	WebSocketClient transport = new StandardWebSocketClient(client);
	WebSocketStompClient stompClient = new WebSocketStompClient(transport);
	stompClient.setMessageConverter(new StringMessageConverter());
	stompClient.setTaskScheduler(new DefaultManagedTaskScheduler());
	StompSessionHandler handler = new BankStompSessionHandler();
	String url = "ws://localhost:8080/stomp-server/swayam";
	stompClient.connect(url, handler);
	stompClient.start();

	try {
	    Thread.sleep(30 * 1000);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
    }

}
