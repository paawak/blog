package com.swayam.demo.stomp.client;

import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

public class WebSocketMessageBrokerClient {

    public static void main(String[] args) {
	WebSocketClient transport = new StandardWebSocketClient();
	WebSocketStompClient stompClient = new WebSocketStompClient(transport);
	stompClient.setMessageConverter(new StringMessageConverter());
	// stompClient.setTaskScheduler(taskScheduler);
    }

}
