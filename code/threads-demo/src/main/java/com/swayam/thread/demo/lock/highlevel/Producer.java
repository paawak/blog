package com.swayam.thread.demo.lock.highlevel;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Producer implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    private final List<String> list;
    private final Lock lock;
    private final Condition condition;

    public Producer(List<String> list, Lock lock, Condition condition) {
        this.list = list;
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        while (true) {
            LOGGER.info("in producer...");
            if (lock.tryLock()) {
                try {
                    list.add("AAAAAAAA");
                    condition.signal();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

}
