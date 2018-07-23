package com.swayam.demo.trx.cmt.service;

import java.util.List;
import java.util.Map;

import com.swayam.demo.trx.cmt.entity.Rating;
import com.swayam.demo.trx.cmt.web.dto.AuthorRatingRequest;

public interface AuthorRatingService {

	List<Rating> getRatings();

	Map<String, Long> addAuthorRating(AuthorRatingRequest authorRatingRequest);

}
