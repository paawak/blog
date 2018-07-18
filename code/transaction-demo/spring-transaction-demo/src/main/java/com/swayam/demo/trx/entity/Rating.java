package com.swayam.demo.trx.entity;

public class Rating {

	private final Long id;
	private final String userName;
	private final long authorId;
	private final int rating;

	public Rating(Long id, String userName, long authorId, int rating) {
		this.id = id;
		this.userName = userName;
		this.authorId = authorId;
		this.rating = rating;
	}

	public Long getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public long getAuthorId() {
		return authorId;
	}

	public int getRating() {
		return rating;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (authorId ^ (authorId >>> 32));
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + rating;
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
		Rating other = (Rating) obj;
		if (authorId != other.authorId)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (rating != other.rating)
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Rating [id=%s, userName=%s, authorId=%s, rating=%s]", id, userName, authorId, rating);
	}

}
