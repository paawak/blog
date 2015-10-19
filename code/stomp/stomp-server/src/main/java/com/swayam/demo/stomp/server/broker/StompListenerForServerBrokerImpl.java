package com.swayam.demo.stomp.server.broker;

import java.io.IOException;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swayam.demo.stomp.server.dto.BankDetail;
import com.swayam.demo.stomp.server.service.StompListenerForServer;

public class StompListenerForServerBrokerImpl implements StompListenerForServer {

    private final ObjectMapper mapper = new ObjectMapper();
    private final SimpMessagingTemplate messagingTemplate;

    public StompListenerForServerBrokerImpl(
	    SimpMessagingTemplate messagingTemplate) {
	this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void sendMessageToServer(BankDetail bankDetail) {
	try {
	    String jsonString = mapper.writeValueAsString(bankDetail);

	    System.out
		    .println("StompListenerForServerBrokerImpl.sendMessageToServer() jsonString: "
			    + jsonString);

	    messagingTemplate.convertAndSend("/queue/bank-details-updates",
		    jsonString);
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}

    }

    @Override
    public void endOfMessages() {
	// TODO
    }

}
