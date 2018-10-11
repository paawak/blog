package com.swayam.demo.trx.mq;

import java.io.IOException;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swayam.demo.trx.dto.AuthorRequest;
import com.swayam.demo.trx.service.BookService;

@Service
public class JmsMessageConsumer implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(JmsMessageConsumer.class);

    private final ObjectMapper mapper;
    private final BookService bookServiceNonTransactional;
    private final BookService bookServiceTransactional;

    public JmsMessageConsumer(ObjectMapper mapper, @Qualifier("bookServiceNonTransactionalImpl") BookService bookServiceNonTransactional,
            @Qualifier("bookServiceTransactionalImpl") BookService bookServiceTransactional) {
        this.mapper = mapper;
        this.bookServiceNonTransactional = bookServiceNonTransactional;
        this.bookServiceTransactional = bookServiceTransactional;
    }

    @Override
    public void onMessage(Message message) {
        AuthorRequest authorRequest;
        try {
            authorRequest = mapper.readValue(((TextMessage) message).getText(), AuthorRequest.class);
        } catch (IOException | JMSException e) {
            throw new RuntimeException(e);
        }

        LOGGER.info("+++++++++++++++++ recieved message: {}", authorRequest);

        Map<String, Long> result;

        if (authorRequest.isTransactional()) {
            result = bookServiceTransactional.addAuthorWithGenre(authorRequest);
        } else {
            result = bookServiceNonTransactional.addAuthorWithGenre(authorRequest);
        }

        LOGGER.info("save result: {}", result);

    }

}
