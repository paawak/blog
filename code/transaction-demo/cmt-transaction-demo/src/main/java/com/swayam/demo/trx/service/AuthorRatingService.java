package com.swayam.demo.trx.service;

import java.util.List;
import java.util.Map;

import com.swayam.demo.trx.entity.Rating;
import com.swayam.demo.trx.web.dto.AuthorRatingRequest;

public interface AuthorRatingService {

	List<Rating> getRatings();

	Map<String, Long> addAuthorRating(AuthorRatingRequest authorRatingRequest);

}
