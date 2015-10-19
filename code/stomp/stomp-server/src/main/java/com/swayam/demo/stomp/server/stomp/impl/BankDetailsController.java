package com.swayam.demo.stomp.server.stomp.impl;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class BankDetailsController {

    @MessageMapping("/bank-details")
    public String handle(String payload) {
	System.out.println("BankDetailsController.handle() payload:" + payload);
	return "[" + System.currentTimeMillis() + "]: " + payload;
    }

}
