package com.swayam.demo.trx.service;

import java.util.List;
import java.util.Map;

import com.swayam.demo.trx.dto.AuthorRequest;
import com.swayam.demo.trx.entity.Author;
import com.swayam.demo.trx.entity.Genre;

public interface BookService {

	List<Genre> getGenres();

	List<Author> getAuthors();

	Map<String, Long> addAuthorWithGenre(AuthorRequest authorRequest);

}
