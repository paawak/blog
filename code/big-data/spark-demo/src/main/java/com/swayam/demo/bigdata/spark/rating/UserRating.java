package com.swayam.demo.bigdata.spark.rating;

import java.io.Serializable;

public class UserRating implements Serializable {

	private static final long serialVersionUID = 1L;

	private final int userId;
	private final int movieId;
	private final int rating;
	private final long timestamp;

	public UserRating(int userId, int movieId, int rating, long timestamp) {
		this.userId = userId;
		this.movieId = movieId;
		this.rating = rating;
		this.timestamp = timestamp;
	}

	public int getUserId() {
		return userId;
	}

	public int getMovieId() {
		return movieId;
	}

	public int getRating() {
		return rating;
	}

	public long getTimestamp() {
		return timestamp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + movieId;
		result = prime * result + rating;
		result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
		result = prime * result + userId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRating other = (UserRating) obj;
		if (movieId != other.movieId)
			return false;
		if (rating != other.rating)
			return false;
		if (timestamp != other.timestamp)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserRating [userId=" + userId + ", movieId=" + movieId + ", rating=" + rating + ", timestamp="
				+ timestamp + "]";
	}

}
