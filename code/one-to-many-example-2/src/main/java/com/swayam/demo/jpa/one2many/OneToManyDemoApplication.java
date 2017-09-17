package com.swayam.demo.jpa.one2many;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.swayam.demo.spring.springbootdemo" })
public class OneToManyDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(OneToManyDemoApplication.class, args);
	}
}
