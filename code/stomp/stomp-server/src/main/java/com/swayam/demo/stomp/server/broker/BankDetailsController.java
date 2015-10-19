package com.swayam.demo.stomp.server.broker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.swayam.demo.stomp.server.dto.BankDetailSortOrder;
import com.swayam.demo.stomp.server.service.BankDetailService;

@Controller
public class BankDetailsController {

    private final SimpMessagingTemplate messagingTemplate;
    private final BankDetailService bankDetailService;

    @Autowired
    public BankDetailsController(SimpMessagingTemplate messagingTemplate,
	    BankDetailService bankDetailService) {
	this.messagingTemplate = messagingTemplate;
	this.bankDetailService = bankDetailService;
    }

    @MessageMapping("/bank-request")
    public void handle(String payload) {
	System.out.println("BankDetailsController.handle() payload:" + payload);
	BankDetailSortOrder group = BankDetailSortOrder.valueOf(payload
		.toUpperCase());
	bankDetailService.getBankDetailsAsync(group,
		new StompListenerForServerBrokerImpl(messagingTemplate));
    }

}
