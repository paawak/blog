package com.swayam.demo.jpa.one2many.dao;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.swayam.demo.jpa.one2many.model.Book;

public class EntityManagerBasedBookDao implements BookDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(EntityManagerBasedBookDao.class);

	private final EntityManager entityManager;

	public EntityManagerBasedBookDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional(readOnly = true)
	@Override
	public Book getBook(Long bookId) {
		LOGGER.info("retrieving book with id: {}", bookId);
		entityManager.clear();
		return entityManager.find(Book.class, bookId);
	}

	@Transactional
	@Override
	public Long saveNewBook(Book book) {
		LOGGER.info("saving book: {}", book);
		entityManager.persist(book);
		return book.getId();
	}

}
