package com.swayam.demo.jpa.one2many.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swayam.demo.jpa.one2many.model.Book;

@Repository
public class BookJpaBasedDao implements BookDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(BookJpaBasedDao.class);

	private final JpaRepository<Book, Long> bookRepo;

	public BookJpaBasedDao(JpaRepository<Book, Long> bookRepo) {
		this.bookRepo = bookRepo;
	}

	@Override
	public Book getBook(Long bookId) {
		LOGGER.debug("retrieving book with id: {}", bookId);
		return bookRepo.findOne(bookId);
	}

	@Override
	public Long saveNewBook(Book book) {
		return bookRepo.saveAndFlush(book).getId();
	}

}
