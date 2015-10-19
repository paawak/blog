package com.swayam.demo.stomp.client.broker;

import java.lang.reflect.Type;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSession.Receiptable;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

public class BankStompSessionHandler implements StompSessionHandler {

    @Override
    public Type getPayloadType(StompHeaders headers) {
	System.out
		.println("1111111111111111111111111 BankStompSessionHandler.getPayloadType()");
	return String.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
	System.out
		.println("22222222222222222222222222222222 BankStompSessionHandler.handleFrame()");
    }

    @Override
    public void afterConnected(StompSession session,
	    StompHeaders connectedHeaders) {
	System.out
		.println("33333333333333333333333333333333 BankStompSessionHandler.afterConnected()");
	Receiptable receiptable = session.send("/app/bank-request", "job");

	System.out
		.println("BankStompSessionHandler.afterConnected() receiptable: "
			+ receiptable);

	session.subscribe("/queue/bank-details-updates",
		new StompFrameHandler() {

		    @Override
		    public void handleFrame(StompHeaders headers, Object payload) {
			System.out
				.println("BankStompSessionHandler.afterConnected(...).new StompFrameHandler() {...}.handleFrame() "
					+ payload);
		    }

		    @Override
		    public Type getPayloadType(StompHeaders headers) {
			return String.class;
		    }
		});
    }

    @Override
    public void handleException(StompSession session, StompCommand command,
	    StompHeaders headers, byte[] payload, Throwable exception) {
	System.out
		.println("4444444444444444444444444444444444 BankStompSessionHandler.handleException()");
	exception.printStackTrace();
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
	System.out
		.println("555555555555555555555555555555 BankStompSessionHandler.handleTransportError()");
	exception.printStackTrace();
    }

}
