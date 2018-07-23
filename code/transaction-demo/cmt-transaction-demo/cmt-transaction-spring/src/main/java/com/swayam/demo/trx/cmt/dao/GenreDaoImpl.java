package com.swayam.demo.trx.cmt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.swayam.demo.trx.cmt.entity.Genre;

@Repository
public class GenreDaoImpl implements GenreDao {

	private final JdbcOperations jdbcOperations;

	public GenreDaoImpl(@Qualifier("postgresJdbcTemplate") JdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
	}

	@Override
	public List<Genre> getGenres() {
		return jdbcOperations.query("select * from genre", (ResultSet resultSet, int row) -> {
			return new Genre(resultSet.getLong("id"), resultSet.getString("short_name"), resultSet.getString("name"));
		});
	}

	@Override
	public long addGenre(Genre genre) {
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		String sql = "insert into genre (short_name, name) values (?, ?)";
		PreparedStatementCreator psc = (Connection con) -> {
			PreparedStatement pstat = con.prepareStatement(sql, new String[] { "id" });
			pstat.setString(1, genre.getShortName());
			pstat.setString(2, genre.getName());
			return pstat;
		};
		jdbcOperations.update(psc, generatedKeyHolder);
		return generatedKeyHolder.getKey().longValue();
	}

}
