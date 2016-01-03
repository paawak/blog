package com.swayam.demo.jini.unsecure.streaming.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan(basePackages = { "com.swayam.demo.jini.unsecure.streaming.client" })
@PropertySource({ "classpath:jini.properties" })
public class RmiClientConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig() {
	return new PropertySourcesPlaceholderConfigurer();
    }

}
