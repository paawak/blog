package com.swayam.demo.trx.cmt.plain.web.rest;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swayam.demo.trx.cmt.plain.entity.Author;
import com.swayam.demo.trx.cmt.plain.entity.Genre;
import com.swayam.demo.trx.cmt.plain.service.BookService;
import com.swayam.demo.trx.cmt.plain.web.dto.AuthorRequest;

//@RestController
//@RequestMapping(path = "/rest")
public class BookRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BookRestController.class);

	private final BookService bookService;

	public BookRestController(BookService bookService) {
		this.bookService = bookService;
	}

//	@GetMapping(path = "/author")
	public List<Author> getAuthors() {
		return bookService.getAuthors();
	}

//	@GetMapping(path = "/genre")
	public List<Genre> getGenre() {
		return bookService.getGenres();
	}

//	@PostMapping(path = "/author-genre", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public Map<String, Long> save(AuthorRequest authorRequest) {
		LOGGER.debug("authorRequest: {}", authorRequest);
		return bookService.addAuthorWithGenre(authorRequest);
	}

}
