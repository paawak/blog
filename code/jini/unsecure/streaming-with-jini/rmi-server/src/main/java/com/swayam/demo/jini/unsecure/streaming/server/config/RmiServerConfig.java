package com.swayam.demo.jini.unsecure.streaming.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan(basePackages = { "com.swayam.demo.jini.unsecure.streaming.server" })
@PropertySource({ "classpath:jdbc.properties", "classpath:jini.properties" })
public class RmiServerConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig() {
	return new PropertySourcesPlaceholderConfigurer();
    }

}
