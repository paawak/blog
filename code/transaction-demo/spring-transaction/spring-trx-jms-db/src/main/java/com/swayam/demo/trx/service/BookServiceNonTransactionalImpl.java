package com.swayam.demo.trx.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.swayam.demo.trx.dao.AuthorDao;
import com.swayam.demo.trx.dao.GenreDao;
import com.swayam.demo.trx.dto.AuthorRequest;
import com.swayam.demo.trx.entity.Author;
import com.swayam.demo.trx.entity.Genre;
import com.swayam.demo.trx.mq.QueuePublisher;

@Service
public class BookServiceNonTransactionalImpl implements BookService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceNonTransactionalImpl.class);

    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final QueuePublisher queuePublisher;

    public BookServiceNonTransactionalImpl(AuthorDao authorDao, GenreDao genreDao, QueuePublisher queuePublisher) {
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        this.queuePublisher = queuePublisher;
    }

    @Override
    public List<Genre> getGenres() {
        return genreDao.getGenres();
    }

    @Override
    public List<Author> getAuthors() {
        return authorDao.getAuthors();
    }

    @Override
    public Map<String, Long> addAuthorWithGenre(AuthorRequest authorRequest) {

        queuePublisher.publish(authorRequest);

        Map<String, Long> map = new HashMap<>();
        // save genre
        long genreId = genreDao.addGenre(new Genre(null, authorRequest.getGenreShortName(), authorRequest.getGenreName()));

        LOGGER.info("genreId: {}", genreId);

        map.put("genreId", genreId);
        // save author
        long authorId = authorDao.addAuthor(new Author(authorRequest.getAuthorId(), authorRequest.getAuthorFirstName(), authorRequest.getAuthorLastName()));
        map.put("authorId", authorId);

        LOGGER.info("authorId: {}", authorId);

        return map;
    }

}
