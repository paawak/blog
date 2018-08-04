package com.swayam.demo.trx.cmt.spring.service;

import java.util.List;
import java.util.Map;

import com.swayam.demo.trx.cmt.spring.entity.Rating;
import com.swayam.demo.trx.cmt.spring.web.dto.AuthorRatingRequest;

public interface AuthorRatingService {

	List<Rating> getRatings();

	Map<String, Long> addAuthorRating(AuthorRatingRequest authorRatingRequest);

}
