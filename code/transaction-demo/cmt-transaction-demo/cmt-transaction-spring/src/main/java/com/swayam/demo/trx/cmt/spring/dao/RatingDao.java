package com.swayam.demo.trx.cmt.spring.dao;

import java.util.List;

import com.swayam.demo.trx.cmt.spring.entity.Rating;

public interface RatingDao {

	List<Rating> getRatings();

	long addRating(Rating rating);

}
