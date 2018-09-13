package com.swayam.thread.demo.deadlock.intrinsic;

import java.util.ArrayList;
import java.util.List;

public class IntrinsicLockDemo {

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
