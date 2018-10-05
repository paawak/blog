package com.swayam.thread.demo.deadlock;

import java.util.concurrent.ExecutorService;

public class WordProcessor implements FileReaderCallback {

    private final ExecutorService executorService;
    private final RandomWordDao randomWordDao;

    public WordProcessor(ExecutorService executorService, RandomWordDao randomWordDao) {
        this.executorService = executorService;
        this.randomWordDao = randomWordDao;
    }

    @Override
    public void charactersRead(String text) {
        executorService.execute(() -> {
            randomWordDao.insert(text);
        });
    }

}
