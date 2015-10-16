package com.swayam.demo.stomp.client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StompConsumer {

    private static final int THREAD_COUNT = 10;

    public static void main(String[] args) {

	ExecutorService executorService = Executors.newFixedThreadPool(20);

	for (int i = 0; i < THREAD_COUNT; i++) {
	    executorService
		    .submit(new SingleStompConsumer(Integer.toString(i)));
	}

	executorService.shutdown();

    }

}
