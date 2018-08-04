package com.swayam.demo.trx.cmt.spring.dao;

import java.util.List;

import com.swayam.demo.trx.cmt.spring.entity.Genre;

public interface GenreDao {

	List<Genre> getGenres();

	long addGenre(Genre genre);

}
