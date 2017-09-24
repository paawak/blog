package com.swayam.demo.jpa.one2many.dao;

import com.swayam.demo.jpa.one2many.model.Book;

public interface BookDao {

	Book getBook(Long bookId);

	Long saveNewBook(Book book);

}
