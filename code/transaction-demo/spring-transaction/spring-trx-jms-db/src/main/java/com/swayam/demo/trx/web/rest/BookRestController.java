package com.swayam.demo.trx.web.rest;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swayam.demo.trx.dto.AuthorRequest;
import com.swayam.demo.trx.entity.Author;
import com.swayam.demo.trx.entity.Genre;
import com.swayam.demo.trx.service.BookService;

@RestController
@RequestMapping(path = "/rest")
public class BookRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookRestController.class);

    private final BookService bookServiceNonTransactional;
    private final BookService bookServiceTransactional;

    public BookRestController(@Qualifier("bookServiceNonTransactionalImpl") BookService bookServiceNonTransactional,
            @Qualifier("bookServiceTransactionalImpl") BookService bookServiceTransactional) {
        this.bookServiceNonTransactional = bookServiceNonTransactional;
        this.bookServiceTransactional = bookServiceTransactional;
    }

    @GetMapping(path = "/author")
    public List<Author> getAuthors() {
        return bookServiceNonTransactional.getAuthors();
    }

    @GetMapping(path = "/genre")
    public List<Genre> getGenre() {
        return bookServiceNonTransactional.getGenres();
    }

    @PostMapping(path = "/author-genre", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Map<String, Long> save(AuthorRequest authorRequest) {
        LOGGER.debug("authorRequest: {}", authorRequest);
        if (authorRequest.isTransactional()) {
            return bookServiceTransactional.addAuthorWithGenre(authorRequest);
        } else {
            return bookServiceNonTransactional.addAuthorWithGenre(authorRequest);
        }
    }

}
