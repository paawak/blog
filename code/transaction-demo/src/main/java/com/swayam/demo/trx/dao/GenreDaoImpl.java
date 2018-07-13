package com.swayam.demo.trx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.swayam.demo.trx.entity.Genre;

@Repository
public class GenreDaoImpl implements GenreDao {

	private final JdbcTemplate jdbcTemplate;

	public GenreDaoImpl(@Qualifier("postgresJdbcTemplate") JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Genre> getGenres() {
		return jdbcTemplate.query("select * from genre", (ResultSet resultSet, int row) -> {
			return new Genre(resultSet.getLong("id"), resultSet.getString("short_name"), resultSet.getString("name"));
		});
	}

	@Override
	public long addGenre(Genre genre) {
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		String sql = "insert into genre (id, short_name, name) values (SEQ_GENRE_ID.nextVal, ?, ?)";
		PreparedStatementCreator psc = (Connection con) -> {
			PreparedStatement pstat = con.prepareStatement(sql);
			pstat.setString(1, genre.getShortName());
			pstat.setString(2, genre.getName());
			return pstat;
		};
		jdbcTemplate.update(psc, generatedKeyHolder);
		return generatedKeyHolder.getKey().longValue();
	}

}
