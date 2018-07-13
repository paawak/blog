package com.swayam.demo.trx.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.swayam.demo.trx.dao.AuthorDao;
import com.swayam.demo.trx.dao.GenreDao;
import com.swayam.demo.trx.entity.Author;
import com.swayam.demo.trx.entity.Genre;
import com.swayam.demo.trx.web.dto.AuthorRequest;

@Service
public class AuthorServiceImpl implements AuthorService {

	private final AuthorDao authorDao;
	private final GenreDao genreDao;

	public AuthorServiceImpl(AuthorDao authorDao, GenreDao genreDao) {
		this.authorDao = authorDao;
		this.genreDao = genreDao;
	}

	@Override
	public List<Genre> getGenres() {
		return genreDao.getGenres();
	}

	@Override
	public List<Author> getAuthors() {
		return authorDao.getAuthors();
	}

	@Override
	public Map<String, String> saveAuthor(AuthorRequest authorRequest) {
		Map<String, String> map = new HashMap<>();
		// save genre
		long genreId = genreDao
				.addGenre(new Genre(null, authorRequest.getGenreShortName(), authorRequest.getGenreName()));
		map.put("genreId", String.valueOf(genreId));
		// save author
		long authorId = authorDao.addAuthor(new Author(authorRequest.getAuthorId(), authorRequest.getAuthorFirstName(),
				authorRequest.getAuthorLastName()));
		map.put("authorId", String.valueOf(authorId));
		return map;
	}

}
