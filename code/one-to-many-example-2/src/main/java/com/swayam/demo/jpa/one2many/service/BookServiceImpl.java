package com.swayam.demo.jpa.one2many.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swayam.demo.jpa.one2many.dao.BookDao;
import com.swayam.demo.jpa.one2many.model.Book;

@Service
public class BookServiceImpl implements BookService {

	private final BookDao bookRepo;

	@Autowired
	public BookServiceImpl(BookDao bookRepo) {
		this.bookRepo = bookRepo;
	}

	@Transactional(readOnly = true)
	@Override
	public Book getBook(Long bookId) {
		return bookRepo.getBook(bookId);
	}

	@Transactional
	@Override
	public Book saveOrUpdate(Book book) {
		Long id = bookRepo.saveNewBook(book);
		return bookRepo.getBook(id);
	}

}
