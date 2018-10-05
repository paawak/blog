package com.swayam.thread.demo.lock.highlevel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class HighlevelLockDemo {

    public static void main(String[] args) throws InterruptedException {

        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        List<String> sharedList = new ArrayList<>();
        Thread consumer = new Thread(new Consumer(sharedList, lock, condition));
        consumer.setName("consumer");
        consumer.start();

        Thread.sleep(2_000);

        Thread producer = new Thread(new Producer(sharedList, lock, condition));
        producer.setName("producer");
        producer.start();

    }

}
