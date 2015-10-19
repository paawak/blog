package com.swayam.demo.stomp.client.handler;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CountDownLatch;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.DeploymentException;

import org.glassfish.tyrus.client.ClientManager;

class SingleStompConsumer implements Runnable {

    private static final String STOMP_URI = "ws://localhost:8080/stomp-server/streaming-bank-details";

    private final String id;

    public SingleStompConsumer(String id) {
	this.id = id;
    }

    @Override
    public void run() {
	CountDownLatch waitTillConnectionClosed = new CountDownLatch(1);

	ClientManager client = ClientManager.createClient();

	Path outputFilePath = Paths.get("d:", "temp", "output_" + id + ".json");

	try (SeekableByteChannel outputFileChannel = Files.newByteChannel(
		outputFilePath, StandardOpenOption.CREATE,
		StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);) {
	    client.connectToServer(new StompClientEndpoint(
		    waitTillConnectionClosed, outputFileChannel),
		    ClientEndpointConfig.Builder.create().build(), new URI(
			    STOMP_URI));
	    waitTillConnectionClosed.await();
	} catch (InterruptedException | IOException | DeploymentException
		| URISyntaxException e) {
	    e.printStackTrace();
	}

	System.out
		.println("StompClientEndpoint.main() All output written in the file: "
			+ outputFilePath);
    }

}