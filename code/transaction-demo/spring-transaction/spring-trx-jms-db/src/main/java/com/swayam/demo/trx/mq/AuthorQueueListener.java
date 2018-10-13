package com.swayam.demo.trx.mq;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swayam.demo.trx.dto.AuthorRequest;
import com.swayam.demo.trx.service.BookService;

@Service
public class AuthorQueueListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorQueueListener.class);

    private final ObjectMapper mapper;
    private final BookService bookService;

    public AuthorQueueListener(ObjectMapper mapper, BookService bookService) {
        this.mapper = mapper;
        this.bookService = bookService;
    }

    @JmsListener(destination = "AUTHOR_REQUEST")
    public void processAuthorRequest(String message) {
        AuthorRequest authorRequest;
        try {
            authorRequest = mapper.readValue(message, AuthorRequest.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        LOGGER.info("+++++++++++++++++ recieved message: {}", authorRequest);

        Map<String, Long> result = bookService.addAuthorWithGenre(authorRequest);

        LOGGER.info("save result: {}", result);

    }

}
