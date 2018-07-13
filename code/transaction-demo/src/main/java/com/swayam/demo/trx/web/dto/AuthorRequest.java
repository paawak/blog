package com.swayam.demo.trx.web.dto;

public class AuthorRequest {

	private final long authorId;
	private final String authorFirstName;
	private final String authorLastName;
	private final String genreShortName;
	private final String genreName;

	public AuthorRequest() {
		this(0, null, null, null, null);
	}

	public AuthorRequest(long authorId, String authorFirstName, String authorLastName, String genreShortName,
			String genreName) {
		this.authorId = authorId;
		this.authorFirstName = authorFirstName;
		this.authorLastName = authorLastName;
		this.genreShortName = genreShortName;
		this.genreName = genreName;
	}

	public long getAuthorId() {
		return authorId;
	}

	public String getAuthorFirstName() {
		return authorFirstName;
	}

	public String getAuthorLastName() {
		return authorLastName;
	}

	public String getGenreShortName() {
		return genreShortName;
	}

	public String getGenreName() {
		return genreName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authorFirstName == null) ? 0 : authorFirstName.hashCode());
		result = prime * result + (int) (authorId ^ (authorId >>> 32));
		result = prime * result + ((authorLastName == null) ? 0 : authorLastName.hashCode());
		result = prime * result + ((genreName == null) ? 0 : genreName.hashCode());
		result = prime * result + ((genreShortName == null) ? 0 : genreShortName.hashCode());
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
		AuthorRequest other = (AuthorRequest) obj;
		if (authorFirstName == null) {
			if (other.authorFirstName != null)
				return false;
		} else if (!authorFirstName.equals(other.authorFirstName))
			return false;
		if (authorId != other.authorId)
			return false;
		if (authorLastName == null) {
			if (other.authorLastName != null)
				return false;
		} else if (!authorLastName.equals(other.authorLastName))
			return false;
		if (genreName == null) {
			if (other.genreName != null)
				return false;
		} else if (!genreName.equals(other.genreName))
			return false;
		if (genreShortName == null) {
			if (other.genreShortName != null)
				return false;
		} else if (!genreShortName.equals(other.genreShortName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format(
				"AuthorRequest [authorId=%s, authorFirstName=%s, authorLastName=%s, genreShortName=%s, genreName=%s]",
				authorId, authorFirstName, authorLastName, genreShortName, genreName);
	}

}
