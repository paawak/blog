
package com.swayam.thread.demo.deadlock;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BigFileReaderRunner {

    public static void main(String[] args) throws IOException, URISyntaxException {

        ExecutorService executorService = Executors.newFixedThreadPool(8);
        RandomWordDao randomWordDao = new RandomWordDao();
        FileReaderCallback fileReaderCallback = new WordProcessor(executorService, randomWordDao);

        new BigFileReader(fileReaderCallback).readFile(Paths.get(BigFileReaderRunner.class.getResource("/bangla_dictionary.sql").toURI()));

    }

}
