package com.swayam.demo.jpa.one2many.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.swayam.demo.jpa.one2many.model.Book;

public class JpaBasedBookDao implements BookDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(JpaBasedBookDao.class);

	private final JpaRepository<Book, Long> bookRepo;

	public JpaBasedBookDao(JpaRepository<Book, Long> bookRepo) {
		this.bookRepo = bookRepo;
	}

	@Transactional(readOnly = true)
	@Override
	public Book getBook(Long bookId) {
		LOGGER.debug("retrieving book with id: {}", bookId);
		return bookRepo.findOne(bookId);
	}

	@Transactional
	@Override
	public Long saveNewBook(Book book) {
		return bookRepo.saveAndFlush(book).getId();
	}

}
