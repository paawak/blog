package com.swayam.demo.stomp.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.GZIPInputStream;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.CloseReason;
import javax.websocket.DeploymentException;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler.Whole;
import javax.websocket.Session;

import org.glassfish.tyrus.client.ClientManager;

public class StompConsumer {

    private static final String STOMP_URI = "ws://localhost:8080/stomp-server/streaming-bank-details";

    private static final int THREAD_COUNT = 10;

    public static void main(String[] args) {

	ExecutorService executorService = Executors.newFixedThreadPool(20);

	for (int i = 0; i < THREAD_COUNT; i++) {
	    executorService
		    .submit(new SingleStompConsumer(Integer.toString(i)));
	}

	executorService.shutdown();

    }

    private static class SingleStompConsumer implements Runnable {

	private final String id;

	public SingleStompConsumer(String id) {
	    this.id = id;
	}

	@Override
	public void run() {
	    CountDownLatch waitTillConnectionClosed = new CountDownLatch(1);

	    ClientManager client = ClientManager.createClient();

	    Path outputFilePath = Paths.get("d:", "temp", "output_" + id
		    + ".json");

	    try (SeekableByteChannel outputFileChannel = Files.newByteChannel(
		    outputFilePath, StandardOpenOption.CREATE,
		    StandardOpenOption.TRUNCATE_EXISTING,
		    StandardOpenOption.WRITE);) {
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

    private static class StompClientEndpoint extends Endpoint {

	private final CountDownLatch waitTillConnectionClosed;
	private final SeekableByteChannel outputFileChannel;

	StompClientEndpoint(CountDownLatch waitTillConnectionClosed,
		SeekableByteChannel outputFileChannel) {
	    this.waitTillConnectionClosed = waitTillConnectionClosed;
	    this.outputFileChannel = outputFileChannel;
	}

	@Override
	public void onOpen(Session session, EndpointConfig config) {

	    session.addMessageHandler(new Whole<InputStream>() {

		@Override
		public void onMessage(InputStream inputStream) {

		    System.out.println("Received message...");

		    try (ReadableByteChannel reportByteChannel = Channels
			    .newChannel(new GZIPInputStream(inputStream));) {

			ByteBuffer byteBuffer = ByteBuffer.allocate(1000);

			while (reportByteChannel.read(byteBuffer) > 0) {
			    byteBuffer.flip();
			    outputFileChannel.write(byteBuffer);
			    byteBuffer.compact();
			}

			outputFileChannel.write(StandardCharsets.US_ASCII
				.encode("\n\n\n"));

		    } catch (IOException e) {
			e.printStackTrace();
		    }

		}
	    });

	    try {
		session.getBasicRemote().sendText("MARITAL_STATUS");
	    } catch (IOException e) {
		e.printStackTrace();
	    }

	}

	@Override
	public void onClose(Session session, CloseReason closeReason) {
	    System.out.println("StompConsumer.StompClientEndpoint.onClose()");
	    waitTillConnectionClosed.countDown();
	}
    }

}
