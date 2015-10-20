package com.swayam.demo.stomp.server.broker;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swayam.demo.stomp.server.dto.BankDetail;
import com.swayam.demo.stomp.server.service.DataListener;

public class DataListenerForStompBrokerImpl implements DataListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataListenerForStompBrokerImpl.class);

    private final ObjectMapper mapper = new ObjectMapper();
    private final SimpMessagingTemplate messagingTemplate;

    public DataListenerForStompBrokerImpl(SimpMessagingTemplate messagingTemplate) {
	this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void sendMessageToClient(BankDetail bankDetail) {
	try {
	    String jsonString = mapper.writeValueAsString(bankDetail);

	    LOGGER.info("trying to publish message `{}` to queue...", jsonString);

	    messagingTemplate.convertAndSend("/queue/bank-details-updates", jsonString);
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}

    }

    @Override
    public void endOfMessages() {
	// TODO
    }

}
