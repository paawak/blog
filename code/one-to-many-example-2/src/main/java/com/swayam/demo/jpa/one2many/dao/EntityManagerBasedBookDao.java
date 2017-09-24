package com.swayam.demo.jpa.one2many.dao;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swayam.demo.jpa.one2many.model.Book;

public class EntityManagerBasedBookDao implements BookDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(EntityManagerBasedBookDao.class);

	private final EntityManager entityManager;

	public EntityManagerBasedBookDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public Book getBook(Long bookId) {
		LOGGER.debug("retrieving book with id: {}", bookId);
		return entityManager.find(Book.class, bookId);
	}

	@Override
	public Long saveNewBook(Book book) {
		entityManager.persist(book);
		return book.getId();
	}

}
