package com.swayam.demo.bigdata.spark.rating;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UserRatingParserTest {

	@Test
	public void testParse() {
		// given
		String userRatingText = "1::1193::5::978300760";
		UserRatingParser testClass = new UserRatingParser();

		// when
		UserRating result = testClass.parse(userRatingText);

		// then
		assertEquals(1, result.getUserId());
		assertEquals(1193, result.getMovieId());
		assertEquals(5, result.getRating());
		assertEquals(978300760L, result.getTimestamp());
	}

}
