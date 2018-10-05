package com.swayam.thread.demo.deadlock.lock;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Consumer implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    private final List<String> list;
    private final Lock lock;
    private final Condition condition;

    public Consumer(List<String> list, Lock lock, Condition condition) {
        this.list = list;
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        while (true) {
            LOGGER.info("in consumer...");
            if (lock.tryLock()) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    LOGGER.error("interrupted", e);
                } finally {
                    lock.unlock();
                }
                list.remove(list.size() - 1);
            }
        }
    }

}
