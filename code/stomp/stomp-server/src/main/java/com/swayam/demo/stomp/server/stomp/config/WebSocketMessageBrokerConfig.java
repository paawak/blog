package com.swayam.demo.stomp.server.stomp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketMessageBrokerConfig extends
	AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
	System.out
		.println("11111111111111111 WebSocketMessageBrokerConfig.configureMessageBroker()");
	config.setApplicationDestinationPrefixes("/app");
	config.enableSimpleBroker("/queue", "/topic");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
	System.out
		.println("22222222222222222 WebSocketMessageBrokerConfig.registerStompEndpoints()");
	registry.addEndpoint("/swayam").setAllowedOrigins("*");
    }

}
