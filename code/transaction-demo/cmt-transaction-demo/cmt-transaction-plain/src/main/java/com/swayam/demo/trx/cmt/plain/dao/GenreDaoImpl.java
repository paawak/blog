package com.swayam.demo.trx.cmt.plain.dao;

import java.util.List;

import com.swayam.demo.trx.cmt.plain.entity.Genre;

public class GenreDaoImpl implements GenreDao {

//	private final JdbcOperations jdbcOperations;
//
//	public GenreDaoImpl(@Qualifier("postgresJdbcTemplate") JdbcOperations jdbcOperations) {
//		this.jdbcOperations = jdbcOperations;
//	}

	@Override
	public List<Genre> getGenres() {
//		return jdbcOperations.query("select * from genre", (ResultSet resultSet, int row) -> {
//			return new Genre(resultSet.getLong("id"), resultSet.getString("short_name"), resultSet.getString("name"));
//		});
		throw new UnsupportedOperationException();
	}

	@Override
	public long addGenre(Genre genre) {
//		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
//		String sql = "insert into genre (short_name, name) values (?, ?)";
//		PreparedStatementCreator psc = (Connection con) -> {
//			PreparedStatement pstat = con.prepareStatement(sql, new String[] { "id" });
//			pstat.setString(1, genre.getShortName());
//			pstat.setString(2, genre.getName());
//			return pstat;
//		};
//		jdbcOperations.update(psc, generatedKeyHolder);
//		return generatedKeyHolder.getKey().longValue();
		throw new UnsupportedOperationException();
	}

}
