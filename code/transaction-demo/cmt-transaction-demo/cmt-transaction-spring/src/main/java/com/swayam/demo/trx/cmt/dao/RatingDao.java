package com.swayam.demo.trx.cmt.dao;

import java.util.List;

import com.swayam.demo.trx.cmt.entity.Rating;

public interface RatingDao {

	List<Rating> getRatings();

	long addRating(Rating rating);

}
