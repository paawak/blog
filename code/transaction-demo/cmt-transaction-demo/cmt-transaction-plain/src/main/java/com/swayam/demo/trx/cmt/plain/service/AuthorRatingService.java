package com.swayam.demo.trx.cmt.plain.service;

import java.util.List;
import java.util.Map;

import com.swayam.demo.trx.cmt.plain.entity.Rating;
import com.swayam.demo.trx.cmt.plain.web.dto.AuthorRatingRequest;

public interface AuthorRatingService {

	List<Rating> getRatings();

	Map<String, Long> addAuthorRating(AuthorRatingRequest authorRatingRequest);

}
