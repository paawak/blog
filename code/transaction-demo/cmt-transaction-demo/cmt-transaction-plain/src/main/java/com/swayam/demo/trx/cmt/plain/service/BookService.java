package com.swayam.demo.trx.cmt.plain.service;

import java.util.List;
import java.util.Map;

import com.swayam.demo.trx.cmt.plain.entity.Author;
import com.swayam.demo.trx.cmt.plain.entity.Genre;
import com.swayam.demo.trx.cmt.plain.web.dto.AuthorRequest;

public interface BookService {

	List<Genre> getGenres();

	List<Author> getAuthors();

	Map<String, Long> addAuthorWithGenre(AuthorRequest authorRequest);

}
