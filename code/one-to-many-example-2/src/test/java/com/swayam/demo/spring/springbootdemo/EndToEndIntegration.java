package com.swayam.demo.spring.springbootdemo;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Set;

import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.assertj.core.util.Sets;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swayam.demo.jpa.one2many.model.Author;
import com.swayam.demo.jpa.one2many.model.Book;
import com.swayam.demo.jpa.one2many.model.Genre;

public class EndToEndIntegration {

	@Test
	public void testBookInsert() throws IOException {
		Request request = Request.Post("http://localhost:8080/books").bodyStream(
				EndToEndIntegration.class.getResourceAsStream("/json/save_book_request_1.json"),
				ContentType.APPLICATION_JSON);
		Response response = request.execute();
		Book savedBook = new ObjectMapper().readValue(response.returnContent().asStream(), Book.class);
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
