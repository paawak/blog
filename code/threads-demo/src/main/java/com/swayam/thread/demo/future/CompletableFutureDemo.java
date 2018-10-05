package com.swayam.thread.demo.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureDemo {

    public static void main(String[] a) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        CompletableFuture<String> longRunningTask = CompletableFuture.supplyAsync(() -> new BlockingTask().invokeLongRunningTask(), executor);
        longRunningTask.whenComplete((String result, Throwable t) -> {

            if (longRunningTask.isCompletedExceptionally()) {
                t.printStackTrace();
            } else {
                System.out.println(result);
            }

            executor.shutdown();

        });

    }

}
