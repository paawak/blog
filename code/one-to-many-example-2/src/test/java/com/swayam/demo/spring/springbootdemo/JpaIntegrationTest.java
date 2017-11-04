package com.swayam.demo.spring.springbootdemo;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Set;

import javax.persistence.EntityTransaction;

import org.assertj.core.util.Sets;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swayam.demo.jpa.one2many.model.Author;
import com.swayam.demo.jpa.one2many.model.Book;
import com.swayam.demo.jpa.one2many.model.Genre;

public class JpaIntegrationTest extends JpaIntegrationTestParent {

	@Test
	public void testBookQuery() {
		Book book = entityManager.find(Book.class, 1L);
		Author author = book.getAuthor();
		assertEquals(10L, author.getId().longValue());
		assertEquals("Arthur Conan", author.getFirstName());
		assertEquals("Doyle", author.getLastName());
		Set<Genre> genres = book.getGenres();
		assertEquals(2, genres.size());

		Genre genre_1 = new Genre();
		genre_1.setId(2L);
		genre_1.setShortName("fc");
		genre_1.setName("fition");
		Genre genre_2 = new Genre();
		genre_2.setId(3L);
		genre_2.setShortName("mys");
		genre_2.setName("mystery");
		assertEquals(Sets.newLinkedHashSet(genre_1, genre_2), genres);
	}

	@Test
	public void testBookInsert_flush() throws JsonParseException, JsonMappingException, IOException {
		Book book = new ObjectMapper()
				.readValue(JpaIntegrationTest.class.getResourceAsStream("/json/save_book_request_1.json"), Book.class);

		EntityTransaction entityTransaction = entityManager.getTransaction();

		entityTransaction.begin();

		entityManager.persist(book);

		entityManager.flush();

		entityTransaction.commit();
		entityManager.clear();

		Book savedBook = entityManager.find(Book.class, book.getId());

		assertSavedBook(savedBook);
	}

	@Test
	public void testBookInsert_simple() throws JsonParseException, JsonMappingException, IOException {
		Book book = new ObjectMapper()
				.readValue(JpaIntegrationTest.class.getResourceAsStream("/json/save_book_request_1.json"), Book.class);

		EntityTransaction entityTransaction = entityManager.getTransaction();

		entityTransaction.begin();

		entityManager.persist(book);

		entityTransaction.commit();

		assertSavedBook(book);
	}

	private void assertSavedBook(Book savedBook) {
		Author author = savedBook.getAuthor();
		assertEquals(10L, author.getId().longValue());
		assertEquals("Arthur Conan", author.getFirstName());
		assertEquals("Doyle", author.getLastName());
		Set<Genre> genres = savedBook.getGenres();
		assertEquals(2, genres.size());

		Genre genre_1 = new Genre();
		genre_1.setId(2L);
		genre_1.setShortName("fc");
		genre_1.setName("fition");
		Genre genre_2 = new Genre();
		genre_2.setId(3L);
		genre_2.setShortName("mys");
		genre_2.setName("mystery");
		assertEquals(Sets.newLinkedHashSet(genre_1, genre_2), genres);
	}

}
