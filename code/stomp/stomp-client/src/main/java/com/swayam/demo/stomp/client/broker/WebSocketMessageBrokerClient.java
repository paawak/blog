package com.swayam.demo.stomp.client.broker;

import org.glassfish.tyrus.client.ClientManager;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

public class WebSocketMessageBrokerClient {

    public static void main(String[] args) {
	ClientManager client = ClientManager.createClient();
	WebSocketClient transport = new StandardWebSocketClient(client);
	WebSocketStompClient stompClient = new WebSocketStompClient(transport);
	// stompClient.setMessageConverter(new StringMessageConverter());
	// stompClient.setTaskScheduler(new DefaultManagedTaskScheduler());
	StompSessionHandler handler = new BankStompSessionHandler();

	String url = "ws://localhost:8080/stomp-server/swayam";
	// String url = "ws://echo.websocket.org";
	stompClient.connect(url, handler);

	try {
	    Thread.sleep(30 * 1000);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
    }

}
