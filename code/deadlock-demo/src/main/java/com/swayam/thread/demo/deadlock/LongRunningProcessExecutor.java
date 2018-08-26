package com.swayam.thread.demo.deadlock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LongRunningProcessExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LongRunningProcessExecutor.class);

    public static void postImageToLocalTesseractOCR() {
        Process p;
        try {
            p = Runtime.getRuntime().exec(
                    "curl -v -X POST -H \"content-type:multipart/form-data\" -F image=@/kaaj/source/porua/tesseract-ocr-docker/sample-images/bangla.jpg -F language=ben http://localhost:8080/rest/ocr");
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

        StringBuilder sb = new StringBuilder();

        while (true) {
            String line;
            try {
                line = reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (line == null) {
                break;
            }
            sb.append(line).append("\n");
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 1);
        }
        String output = sb.toString();
        LOGGER.debug("output from curl: {}", output);
    }

}
