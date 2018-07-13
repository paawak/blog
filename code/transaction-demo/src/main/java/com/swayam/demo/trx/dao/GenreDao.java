package com.swayam.demo.trx.dao;

import java.util.List;

import com.swayam.demo.trx.entity.Genre;

public interface GenreDao {

	List<Genre> getGenres();

	long addGenre(Genre genre);

}
