package com.swayam.demo.stomp.server.broker.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
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
	String jsonString;
	try {
	    jsonString = mapper.writeValueAsString(bankDetail);
	} catch (JsonProcessingException e) {
	    throw new RuntimeException(e);
	}

	LOGGER.info("trying to publish message `{}` to queue...", jsonString);

	messagingTemplate.convertAndSend("/queue/bank-details-updates", jsonString);

    }

    @Override
    public void endOfMessages() {
	LOGGER.info("signalling end of message");
	messagingTemplate.convertAndSend("/queue/bank-details-updates", "END_OF_MESSAGE");
    }

}
