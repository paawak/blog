
package com.swayam.thread.demo.deadlock;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class BigFileReaderRunner {

    public static void main(String[] args) throws IOException, URISyntaxException {
        new BigFileReader((String text) -> {
            System.out.println("`" + text + "`");
        }).readFile(Paths.get(BigFileReaderRunner.class.getResource("/log4j2.xml").toURI()));

    }

}
