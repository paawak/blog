package com.swayam.demo.trx.cmt.spring.dao;

import java.util.List;

import com.swayam.demo.trx.cmt.spring.entity.Author;

public interface AuthorDao {

	List<Author> getAuthors();

	long addAuthor(Author author);

}
