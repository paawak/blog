package com.swayam.demo.trx.cmt.service;

import java.util.List;
import java.util.Map;

import com.swayam.demo.trx.cmt.entity.Author;
import com.swayam.demo.trx.cmt.entity.Genre;
import com.swayam.demo.trx.cmt.web.dto.AuthorRequest;

public interface BookService {

	List<Genre> getGenres();

	List<Author> getAuthors();

	Map<String, Long> addAuthorWithGenre(AuthorRequest authorRequest);

}
