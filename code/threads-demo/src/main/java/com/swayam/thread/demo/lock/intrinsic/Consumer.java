package com.swayam.thread.demo.lock.intrinsic;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Consumer implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    private final List<String> list;

    public Consumer(List<String> list) {
        this.list = list;
    }

    @Override
    public void run() {
        while (true) {
            LOGGER.info("in consumer...");
            synchronized (list) {
                try {
                    list.wait();
                } catch (InterruptedException e) {
                    LOGGER.error("interrupted", e);
                }
                list.remove(list.size() - 1);
            }
        }
    }

}
