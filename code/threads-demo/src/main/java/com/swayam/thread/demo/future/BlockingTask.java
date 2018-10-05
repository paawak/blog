package com.swayam.thread.demo.future;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class BlockingTask {

    public String invokeLongRunningTask() {
        HttpEntity multipartEntity = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                .addTextBody("language", "ben", ContentType.DEFAULT_BINARY).addBinaryBody("image",
                        Paths.get("/kaaj/source/porua/tesseract-ocr-docker/sample-images/bangla.jpg").toFile(), ContentType.DEFAULT_BINARY, "bangla.jpg")
                .build();

        Request request =
                Request.Post("http://localhost:8080/rest/ocr").addHeader("content-type", ContentType.MULTIPART_FORM_DATA.getMimeType()).body(multipartEntity);

        CloseableHttpClient httpClient = HttpClients.createDefault();

        Executor executor = Executor.newInstance(httpClient);

        HttpResponse response;

        try {
            response = executor.execute(request).returnResponse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int responseCode = response.getStatusLine().getStatusCode();

        InputStream responseStream;

        try {
            responseStream = response.getEntity().getContent();
        } catch (UnsupportedOperationException | IOException e) {
            throw new RuntimeException(e);
        }

        StringBuilder responseAsString = new StringBuilder();

        try (Scanner scanner = new Scanner(responseStream)) {
            while (scanner.hasNextLine()) {
                responseAsString.append(scanner.nextLine()).append("\n");
            }
        }

        if (responseCode != 200) {
            throw new RuntimeException("Error in Http Response, got a HTTP code of: " + responseCode + "\n Details: " + responseAsString.toString());
        }

        return responseAsString.toString();

    }

}
