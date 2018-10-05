package com.swayam.thread.demo.future;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Paths;
import java.time.Duration;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

public class BlockingTask {

    public String invokeLongRunningTask() {

        HttpClient client = HttpClient.newBuilder().version(Version.HTTP_1_1).connectTimeout(Duration.ofSeconds(20)).build();

        HttpRequest request = HttpRequest.newBuilder().header("content-type", ContentType.MULTIPART_FORM_DATA.getMimeType())
                .uri(URI.create("http://localhost:8080/rest/ocr")).POST(getMultipartFormData()).build();
        HttpResponse<String> response;
        try {
            response = client.send(request, BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        int status = response.statusCode();

        if (status != 200) {
            throw new RuntimeException("Error in Http Response, got a HTTP code of: " + status);
        }

        return response.body();

    }

    private BodyPublisher getMultipartFormData() {
        HttpEntity multipartEntity = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                .addTextBody("language", "ben", ContentType.DEFAULT_BINARY).addBinaryBody("image",
                        Paths.get("/kaaj/source/porua/tesseract-ocr-docker/sample-images/bangla.jpg").toFile(), ContentType.DEFAULT_BINARY, "bangla.jpg")
                .build();
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        try {
            multipartEntity.writeTo(bao);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return BodyPublishers.ofByteArray(bao.toByteArray());
    }

}
