package com.swayam.demo.stomp.client.broker;

import java.lang.reflect.Type;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

public class BankStompSessionHandler implements StompSessionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankStompSessionHandler.class);

    private final CountDownLatch waitForEndOfMessage;

    public BankStompSessionHandler(CountDownLatch waitForEndOfMessage) {
	this.waitForEndOfMessage = waitForEndOfMessage;
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
	return String.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
	// TODO
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
	LOGGER.info("now connected to server");
	session.send("/app/bank-request", "job");

	session.subscribe("/queue/bank-details-updates", new StompFrameHandler() {

	    @Override
	    public void handleFrame(StompHeaders headers, Object payload) {
		LOGGER.info("recieved message: `{}` from server", payload);
		if (payload.equals("END_OF_MESSAGE")) {
		    waitForEndOfMessage.countDown();
		}
	    }

	    @Override
	    public Type getPayloadType(StompHeaders headers) {
		return String.class;
	    }
	});
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
	LOGGER.error("error", exception);
	waitForEndOfMessage.countDown();
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
	LOGGER.error("error", exception);
	waitForEndOfMessage.countDown();
    }

}
