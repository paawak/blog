package com.swayam.demo.stomp.server.stomp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import com.swayam.demo.stomp.server.service.BankDetailService;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketMessageBrokerConfig extends
	AbstractWebSocketMessageBrokerConfigurer {

    @Autowired
    private BankDetailService bankDetailService;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
	config.setApplicationDestinationPrefixes("/app");
	config.enableSimpleBroker("/queue");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
	registry.addEndpoint("/portfolio").withSockJS();
    }

}
