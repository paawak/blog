package com.swayam.demo.spring.springbootdemo;

import java.io.IOException;

import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swayam.demo.jpa.one2many.model.Book;

public class EndToEndIntegration {

	@Test
	public void testBookInsert_complex() throws IOException {
		Request request = Request.Post("http://localhost:8080/books").bodyStream(
				EndToEndIntegration.class.getResourceAsStream("/json/save_new_book_request_complex.json"),
				ContentType.APPLICATION_JSON);
		Response response = request.execute();
		Book savedBook = new ObjectMapper().readValue(response.returnContent().asStream(), Book.class);
		JpaIntegrationTest.assertSavedBook(savedBook);
	}

	@Test
	public void testBookInsert_simple() throws IOException {
		Request request = Request.Post("http://localhost:8080/books").bodyStream(
				EndToEndIntegration.class.getResourceAsStream("/json/save_new_book_request_light.json"),
				ContentType.APPLICATION_JSON);
		Response response = request.execute();
		Book savedBook = new ObjectMapper().readValue(response.returnContent().asStream(), Book.class);
		JpaIntegrationTest.assertSavedBook(savedBook);
	}

}
