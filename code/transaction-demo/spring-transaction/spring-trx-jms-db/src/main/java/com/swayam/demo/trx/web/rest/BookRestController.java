package com.swayam.demo.trx.web.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swayam.demo.trx.dto.AuthorRequest;
import com.swayam.demo.trx.entity.Author;
import com.swayam.demo.trx.entity.Genre;
import com.swayam.demo.trx.mq.QueuePublisher;
import com.swayam.demo.trx.service.BookService;

@RestController
@RequestMapping(path = "/rest")
public class BookRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookRestController.class);

    private final BookService bookService;
    private final QueuePublisher queuePublisher;

    public BookRestController(BookService bookService, QueuePublisher queuePublisher) {
        this.bookService = bookService;
        this.queuePublisher = queuePublisher;
    }

    @GetMapping(path = "/author")
    public List<Author> getAuthors() {
        return bookService.getAuthors();
    }

    @GetMapping(path = "/genre")
    public List<Genre> getGenre() {
        return bookService.getGenres();
    }

    @PostMapping(path = "/author-genre", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String save(AuthorRequest authorRequest) {
        LOGGER.debug("authorRequest: {}", authorRequest);
        queuePublisher.publish(authorRequest);
        return "Successfully published to JMS Queue";
    }

}
