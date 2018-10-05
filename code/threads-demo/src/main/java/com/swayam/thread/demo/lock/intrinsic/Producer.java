package com.swayam.thread.demo.lock.intrinsic;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Producer implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    private final List<String> list;

    public Producer(List<String> list) {
        this.list = list;
    }

    @Override
    public void run() {
        while (true) {
            LOGGER.info("in producer...");
            synchronized (list) {
                list.add("AAAAAAAA");
                list.notify();
            }
        }
    }

}
