package com.swayam.demo.trx.cmt.plain.dao;

import java.util.List;

import com.swayam.demo.trx.cmt.plain.entity.Author;

public interface AuthorDao {

	List<Author> getAuthors();

	long addAuthor(Author author);

}
