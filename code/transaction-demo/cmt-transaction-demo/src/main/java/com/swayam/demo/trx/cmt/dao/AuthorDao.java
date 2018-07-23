package com.swayam.demo.trx.cmt.dao;

import java.util.List;

import com.swayam.demo.trx.cmt.entity.Author;

public interface AuthorDao {

	List<Author> getAuthors();

	long addAuthor(Author author);

}
