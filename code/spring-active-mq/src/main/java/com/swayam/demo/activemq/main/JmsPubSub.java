package com.swayam.demo.activemq.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.swayam.demo.activemq.config.ActiveMQConfig;
import com.swayam.demo.activemq.model.BankDetailSortOrder;
import com.swayam.demo.activemq.service.DataPublisherService;

public class JmsPubSub {

    public static void main(String[] args) {
	try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ActiveMQConfig.class);) {
	    DataPublisherService dataPublisherService = context.getBean(DataPublisherService.class);
	    dataPublisherService.publishData(BankDetailSortOrder.JOB);
	}
    }

}
