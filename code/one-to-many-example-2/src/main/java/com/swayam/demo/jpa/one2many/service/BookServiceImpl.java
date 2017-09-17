package com.swayam.demo.jpa.one2many.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swayam.demo.jpa.one2many.model.Book;

public class BookServiceImpl implements BookService {

	private final JpaRepository<Book, Long> bookRepo;

	public BookServiceImpl(JpaRepository<Book, Long> bookRepo) {
		this.bookRepo = bookRepo;
	}

	@Override
	public Book getBook(Long bookId) {
		return bookRepo.findOne(bookId);
	}

}
