package com.swayam.demo.trx.cmt.web.dto;

public class AuthorRequest {

	private long authorId;
	private String authorFirstName;
	private String authorLastName;
	private String genreShortName;
	private String genreName;

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

	public void setAuthorId(long authorId) {
		this.authorId = authorId;
	}

	public void setAuthorFirstName(String authorFirstName) {
		this.authorFirstName = authorFirstName;
	}

	public void setAuthorLastName(String authorLastName) {
		this.authorLastName = authorLastName;
	}

	public void setGenreShortName(String genreShortName) {
		this.genreShortName = genreShortName;
	}

	public void setGenreName(String genreName) {
		this.genreName = genreName;
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
