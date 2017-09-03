package com.swayam.demo.bigdata.spark.rating;

import java.io.Serializable;

public class UserRatingParser implements Serializable {

	private static final long serialVersionUID = 1L;

	public UserRating parse(String userRatingText) {
		String[] tokens = userRatingText.split("::");
		if (tokens.length != 4) {
			throw new IllegalArgumentException(String.format("expecting %d tokens, got %d", 4, tokens.length));
		}
		return new UserRating(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]),
				Long.parseLong(tokens[3]));
	}

}
