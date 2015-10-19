package com.swayam.demo.stomp.server.stomp.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class BankDetailsController {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public BankDetailsController(SimpMessagingTemplate messagingTemplate) {
	this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/bank-request")
    public void handle(String payload) {
	System.out.println("BankDetailsController.handle() payload:" + payload);
	String message = "[" + System.currentTimeMillis() + "]: " + payload;
	messagingTemplate
		.convertAndSend("/topic/bank-details-updates", message);
    }

}
