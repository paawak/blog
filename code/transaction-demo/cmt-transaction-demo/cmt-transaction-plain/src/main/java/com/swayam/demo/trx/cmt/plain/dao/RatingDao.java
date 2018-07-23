package com.swayam.demo.trx.cmt.plain.dao;

import java.util.List;

import com.swayam.demo.trx.cmt.plain.entity.Rating;

public interface RatingDao {

	List<Rating> getRatings();

	long addRating(Rating rating);

}
