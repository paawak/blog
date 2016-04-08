package com.swayam.demo.stomp.client.broker.simple;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import javax.websocket.DeploymentException;

import org.glassfish.tyrus.client.ClientManager;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

public class WebSocketMessageBrokerClient {

    public static void main(String[] args) throws DeploymentException, IOException, InterruptedException {
	CountDownLatch waitForEndOfMessage = new CountDownLatch(1);
	ClientManager client = ClientManager.createClient();
	WebSocketClient transport = new StandardWebSocketClient(client);
	WebSocketStompClient stompClient = new WebSocketStompClient(transport);
	stompClient.setMessageConverter(new StringMessageConverter());
	StompSessionHandler handler = new BankStompSessionHandler(waitForEndOfMessage);
	String url = "ws://localhost:8080/stomp-server/swayam";
	stompClient.connect(url, handler);
	waitForEndOfMessage.await();
    }

}
