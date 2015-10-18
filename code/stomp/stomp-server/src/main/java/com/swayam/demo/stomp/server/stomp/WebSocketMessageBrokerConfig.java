package com.swayam.demo.stomp.server.stomp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

import com.swayam.demo.stomp.server.service.BankDetailService;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketMessageBrokerConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Autowired
    private BankDetailService bankDetailService;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
	config.setApplicationDestinationPrefixes("/app");
	config.enableSimpleBroker("/topic", "/queue");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
	registry.addEndpoint("/portfolio").withSockJS();
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
	ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
	container.setMaxTextMessageBufferSize(100_000);
	container.setMaxBinaryMessageBufferSize(100_000);
	container.setAsyncSendTimeout(100_000_000_000_000L);
	container.setMaxSessionIdleTimeout(100_000_000_000_000L);
	return container;
    }

}
