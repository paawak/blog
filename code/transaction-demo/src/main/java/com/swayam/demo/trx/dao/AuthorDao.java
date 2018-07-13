package com.swayam.demo.trx.dao;

import java.util.List;

import com.swayam.demo.trx.entity.Author;

public interface AuthorDao {

	List<Author> getAuthors();

	long addAuthor(Author author);

}
