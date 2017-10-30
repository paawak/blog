package com.swayam.demo.spring.springbootdemo;

import java.io.IOException;

import javax.persistence.EntityTransaction;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swayam.demo.jpa.one2many.model.Book;

public class JpaIntegrationTest extends JpaIntegrationTestParent {

	@Test
	public void testBookQuery() {
		Book book = entityManager.find(Book.class, 1L);
		System.out.println(book);
	}

	@Test
	public void testBookInsert() throws JsonParseException, JsonMappingException, IOException {
		Book book = new ObjectMapper()
				.readValue(JpaIntegrationTest.class.getResourceAsStream("/json/save_book_request_1.json"), Book.class);

		EntityTransaction entityTransaction = entityManager.getTransaction();

		entityTransaction.begin();

		entityManager.persist(book);

		entityManager.flush();

		entityTransaction.commit();
		entityManager.clear();
		System.out.println(book);

		System.out.println("*****************************************");

		Book savedBook = entityManager.find(Book.class, book.getId());
		System.out.println(savedBook);
	}

}
