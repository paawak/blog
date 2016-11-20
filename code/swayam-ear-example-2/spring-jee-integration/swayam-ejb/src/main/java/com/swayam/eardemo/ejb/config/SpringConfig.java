package com.swayam.eardemo.ejb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.swayam.eardemo.ejb")
@PropertySource("classpath:jdbc.properties")
public class SpringConfig {

}
