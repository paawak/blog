package com.swayam.demo.trx.dao;

import java.sql.ResultSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.swayam.demo.trx.model.Author;

@Repository
public class AuthorDaoImpl implements AuthorDao {

	private final JdbcTemplate jdbcTemplate;

	public AuthorDaoImpl(@Qualifier("postgresJdbcTemplate") JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Author> getAuthors() {
		return jdbcTemplate.query("select * from author", (ResultSet resultSet, int row) -> {
			return new Author(resultSet.getLong("id"), resultSet.getString("first_name"),
					resultSet.getString("last_name"));
		});
	}

}
