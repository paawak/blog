package com.swayam.demo.trx.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swayam.demo.trx.dao.AuthorDao;
import com.swayam.demo.trx.dao.GenreDao;
import com.swayam.demo.trx.web.dto.AuthorRequest;

@Service
public class BookServiceTransactionalImpl extends BookServiceNonTransactionalImpl {

    public BookServiceTransactionalImpl(AuthorDao authorDao, GenreDao genreDao) {
        super(authorDao, genreDao);
    }

    @Transactional
    @Override
    public Map<String, Long> addAuthorWithGenre(AuthorRequest authorRequest) {
        return super.addAuthorWithGenre(authorRequest);
    }

}
