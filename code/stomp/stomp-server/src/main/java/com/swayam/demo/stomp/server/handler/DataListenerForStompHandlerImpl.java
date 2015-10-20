package com.swayam.demo.stomp.server.handler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swayam.demo.stomp.server.dto.BankDetail;
import com.swayam.demo.stomp.server.service.DataListener;

public class DataListenerForStompHandlerImpl implements DataListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataListenerForStompHandlerImpl.class);

    private final ObjectMapper mapper = new ObjectMapper();
    private final WebSocketSession session;

    public DataListenerForStompHandlerImpl(WebSocketSession session) {
	this.session = session;
    }

    @Override
    public void sendMessageToClient(BankDetail bankDetail) {
	try {
	    String jsonString = mapper.writeValueAsString(bankDetail);

	    LOGGER.info("trying to apply gzip on message `{}` before sending to the client...", jsonString);

	    ByteArrayOutputStream buffer = new ByteArrayOutputStream(10_000);

	    GZIPOutputStream gzipOutput = new GZIPOutputStream(buffer);
	    gzipOutput.write(jsonString.getBytes());
	    gzipOutput.flush();
	    gzipOutput.close();

	    WebSocketMessage<?> messageToBeSent = new BinaryMessage(buffer.toByteArray());
	    session.sendMessage(messageToBeSent);
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}

    }

    @Override
    public void endOfMessages() {
	try {
	    session.close();
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

}
