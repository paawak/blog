package com.swayam.demo.stomp.client.handler;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.zip.GZIPInputStream;

import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler.Whole;
import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class StompClientEndpoint extends Endpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(StompClientEndpoint.class);

    private final CountDownLatch waitTillConnectionClosed;
    private final SeekableByteChannel outputFileChannel;

    StompClientEndpoint(CountDownLatch waitTillConnectionClosed, SeekableByteChannel outputFileChannel) {
	this.waitTillConnectionClosed = waitTillConnectionClosed;
	this.outputFileChannel = outputFileChannel;
    }

    @Override
    public void onOpen(Session session, EndpointConfig config) {

	session.addMessageHandler(new Whole<InputStream>() {

	    @Override
	    public void onMessage(InputStream inputStream) {

		LOGGER.info("recieved binary message from server");

		try (ReadableByteChannel reportByteChannel = Channels.newChannel(new GZIPInputStream(inputStream));) {

		    ByteBuffer byteBuffer = ByteBuffer.allocate(1000);

		    while (reportByteChannel.read(byteBuffer) > 0) {
			byteBuffer.flip();
			outputFileChannel.write(byteBuffer);
			byteBuffer.compact();
		    }

		    outputFileChannel.write(StandardCharsets.US_ASCII.encode("\n\n\n"));

		} catch (IOException e) {
		    LOGGER.error("error", e);

		}

	    }
	});

	try {
	    session.getBasicRemote().sendText("MARITAL_STATUS");
	} catch (IOException e) {
	    LOGGER.error("error sending message to client", e);
	}

    }

    @Override
    public void onClose(Session session, CloseReason closeReason) {
	LOGGER.warn("Connection closed with reason:{} ", closeReason);
	waitTillConnectionClosed.countDown();
    }
}