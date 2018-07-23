package com.swayam.demo.trx.cmt.plain.dao;

import java.util.List;

import com.swayam.demo.trx.cmt.plain.entity.Genre;

public interface GenreDao {

	List<Genre> getGenres();

	long addGenre(Genre genre);

}
