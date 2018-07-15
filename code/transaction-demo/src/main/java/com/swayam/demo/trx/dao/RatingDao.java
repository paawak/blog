package com.swayam.demo.trx.dao;

import java.util.List;

import com.swayam.demo.trx.entity.Rating;

public interface RatingDao {

	List<Rating> getRatings();

	long addRating(Rating rating);

}
