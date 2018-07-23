package com.swayam.demo.trx.cmt.plain.dao;

import java.util.List;

import com.swayam.demo.trx.cmt.plain.entity.Author;

public class AuthorDaoImpl implements AuthorDao {

//	private final JdbcOperations jdbcOperations;
//
//	public AuthorDaoImpl(@Qualifier("postgresJdbcTemplate") JdbcOperations jdbcOperations) {
//		this.jdbcOperations = jdbcOperations;
//	}

	@Override
	public List<Author> getAuthors() {
//		return jdbcOperations.query("select * from author", (ResultSet resultSet, int row) -> {
//			return new Author(resultSet.getLong("id"), resultSet.getString("first_name"),
//					resultSet.getString("last_name"));
//		});
		throw new UnsupportedOperationException();
	}

	@Override
	public long addAuthor(Author author) {
//		String sql = "insert into author (id, first_name, last_name) values (?, ?, ?)";
//		jdbcOperations.update(sql, author.getId(), author.getFirstName(), author.getLastName());
//		return author.getId();
		throw new UnsupportedOperationException();
	}

}
