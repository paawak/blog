package com.swayam.thread.demo.deadlock;

import java.util.ArrayList;
import java.util.List;

public class DeadLockDemo {

    public static void main(String[] args) throws InterruptedException {
        List<String> sharedList = new ArrayList<>();
        Thread consumer = new Thread(new Consumer(sharedList));
        consumer.setName("consumer");
        consumer.start();

        Thread.sleep(2_000);

        Thread producer = new Thread(new Producer(sharedList));
        producer.setName("producer");
        producer.start();

    }

}
