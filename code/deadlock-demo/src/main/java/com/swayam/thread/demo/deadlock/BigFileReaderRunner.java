
package com.swayam.thread.demo.deadlock;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BigFileReaderRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(BigFileReaderRunner.class);

    private static final String FILE_PATH = "/kaaj/source/blog/code/deadlock-demo/src/main/resources/data/bangla_dictionary.sql";

    public static void main(String[] args) throws IOException, URISyntaxException {

        ExecutorService executorService = Executors.newFixedThreadPool(8);
        RandomWordDao randomWordDao = new RandomWordDao();
        FileReaderCallback fileReaderCallback = new WordProcessor(executorService, randomWordDao);

        Path path = Paths.get(FILE_PATH);

        LOGGER.info("reading from path: {}", path);

        new BigFileReader(fileReaderCallback).readFile(path);

    }

}
