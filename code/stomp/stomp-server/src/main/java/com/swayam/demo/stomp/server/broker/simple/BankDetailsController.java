package com.swayam.demo.stomp.server.broker.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.swayam.demo.stomp.server.dto.BankDetailSortOrder;
import com.swayam.demo.stomp.server.service.BankDetailService;

@Controller
public class BankDetailsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankDetailsController.class);

    private final SimpMessagingTemplate messagingTemplate;
    private final BankDetailService bankDetailService;

    @Autowired
    public BankDetailsController(SimpMessagingTemplate messagingTemplate, BankDetailService bankDetailService) {
	this.messagingTemplate = messagingTemplate;
	this.bankDetailService = bankDetailService;
    }

    @MessageMapping("/bank-request")
    public void handle(String payload) {
	LOGGER.info("got message `{}` from client", payload);
	BankDetailSortOrder group = BankDetailSortOrder.valueOf(payload.toUpperCase());
	bankDetailService.getBankDetailsAsync(group, new DataListenerForStompBrokerImpl(messagingTemplate));
    }

}
