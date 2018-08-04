package com.swayam.demo.trx.cmt.spring.service;

import java.util.List;
import java.util.Map;

import com.swayam.demo.trx.cmt.spring.entity.Author;
import com.swayam.demo.trx.cmt.spring.entity.Genre;
import com.swayam.demo.trx.cmt.spring.web.dto.AuthorRequest;

public interface BookService {

	List<Genre> getGenres();

	List<Author> getAuthors();

	Map<String, Long> addAuthorWithGenre(AuthorRequest authorRequest);

}
