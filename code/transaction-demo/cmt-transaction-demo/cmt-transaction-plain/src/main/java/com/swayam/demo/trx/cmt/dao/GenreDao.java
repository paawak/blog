package com.swayam.demo.trx.cmt.dao;

import java.util.List;

import com.swayam.demo.trx.cmt.entity.Genre;

public interface GenreDao {

	List<Genre> getGenres();

	long addGenre(Genre genre);

}
