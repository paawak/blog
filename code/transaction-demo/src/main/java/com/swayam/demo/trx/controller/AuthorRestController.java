package com.swayam.demo.trx.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.swayam.demo.trx.dao.AuthorDao;
import com.swayam.demo.trx.model.Author;

@RestController
public class AuthorRestController {

	private final AuthorDao authorDao;

	public AuthorRestController(AuthorDao authorDao) {
		this.authorDao = authorDao;
	}

	@RequestMapping(path = { "/author" }, method = { RequestMethod.GET })
	public List<Author> getAuthors() {
		return authorDao.getAuthors();
	}

}
