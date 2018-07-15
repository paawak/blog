package com.swayam.demo.trx.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swayam.demo.trx.dao.AuthorDao;
import com.swayam.demo.trx.dao.RatingDao;
import com.swayam.demo.trx.entity.Author;
import com.swayam.demo.trx.entity.Rating;
import com.swayam.demo.trx.web.dto.AuthorRatingRequest;

@Service
public class AuthorRatingServiceImpl implements AuthorRatingService {

	private final RatingDao ratingDao;
	private final AuthorDao authorDao;

	public AuthorRatingServiceImpl(RatingDao ratingDao, AuthorDao authorDao) {
		this.ratingDao = ratingDao;
		this.authorDao = authorDao;
	}

	@Override
	public List<Rating> getRatings() {
		return ratingDao.getRatings();
	}

	@Transactional(value = "postgresTxManager")
	@Override
	public Map<String, Long> addAuthorRating(AuthorRatingRequest authorRatingRequest) {
		long ratingId = ratingDao.addRating(new Rating(null, authorRatingRequest.getUserName(),
				authorRatingRequest.getAuthorId(), authorRatingRequest.getRating()));

		long authorId = authorDao.addAuthor(new Author(authorRatingRequest.getAuthorId(),
				authorRatingRequest.getAuthorFirstName(), authorRatingRequest.getAuthorLastName()));

		Map<String, Long> map = new HashMap<>();
		map.put("ratingId", ratingId);
		map.put("authorId", authorId);
		return map;
	}

}
