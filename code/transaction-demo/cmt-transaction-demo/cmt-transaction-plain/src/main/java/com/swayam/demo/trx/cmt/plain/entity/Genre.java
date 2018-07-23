package com.swayam.demo.trx.cmt.plain.entity;

public class Genre {

	private final Long id;
	private final String shortName;
	private final String name;

	public Genre(Long id, String shortName, String name) {
		this.id = id;
		this.shortName = shortName;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getShortName() {
		return shortName;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((shortName == null) ? 0 : shortName.hashCode());
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
		Genre other = (Genre) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (shortName == null) {
			if (other.shortName != null)
				return false;
		} else if (!shortName.equals(other.shortName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Genre [id=%s, shortName=%s, name=%s]", id, shortName, name);
	}

}
