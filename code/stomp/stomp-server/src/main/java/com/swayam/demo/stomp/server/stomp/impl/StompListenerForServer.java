package com.swayam.demo.stomp.server.stomp.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swayam.demo.stomp.server.dto.BankDetail;

public class StompListenerForServer {

    private final ObjectMapper mapper = new ObjectMapper();
    private final WebSocketSession session;

    public StompListenerForServer(WebSocketSession session) {
	this.session = session;
    }

    public void sendMessageToServer(BankDetail bankDetail) {
	try {
	    String jsonString = mapper.writeValueAsString(bankDetail);
	    System.out
		    .println("StompListenerForServer.sendMessageToServer() jsonString: "
			    + jsonString);
	    ByteArrayOutputStream buffer = new ByteArrayOutputStream(10_000);

	    GZIPOutputStream gzipOutput = new GZIPOutputStream(buffer);
	    gzipOutput.write(jsonString.getBytes());
	    gzipOutput.flush();
	    gzipOutput.close();

	    WebSocketMessage<?> messageToBeSent = new BinaryMessage(
		    buffer.toByteArray());
	    session.sendMessage(messageToBeSent);
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}

    }

    public void endOfMessages() {
	try {
	    session.close();
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

}
