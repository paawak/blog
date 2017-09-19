package com.swayam.demo.jpa.one2many.service;

import com.swayam.demo.jpa.one2many.model.Book;

public interface BookService {

	Book getBook(Long bookId);

	Book saveOrUpdate(Book book);

}
