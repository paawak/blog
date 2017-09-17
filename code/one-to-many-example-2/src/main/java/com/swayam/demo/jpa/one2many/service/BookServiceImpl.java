package com.swayam.demo.jpa.one2many.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.swayam.demo.jpa.one2many.model.Book;

@Service
public class BookServiceImpl implements BookService {

	private final JpaRepository<Book, Long> bookRepo;

	@Autowired
	public BookServiceImpl(JpaRepository<Book, Long> bookRepo) {
		this.bookRepo = bookRepo;
	}

	@Override
	public Book getBook(Long bookId) {
		return bookRepo.findOne(bookId);
	}

}
