package com.swayam.demo.jpa.one2many.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swayam.demo.jpa.one2many.model.Book;

@Repository
public class BookDaoImpl implements BookDao {

	private final JpaRepository<Book, Long> bookRepo;

	public BookDaoImpl(JpaRepository<Book, Long> bookRepo) {
		this.bookRepo = bookRepo;
	}

	@Override
	public Book findOne(Long bookId) {
		return bookRepo.findOne(bookId);
	}

	@Override
	public Book save(Book book) {
		return bookRepo.save(book);
	}

}
