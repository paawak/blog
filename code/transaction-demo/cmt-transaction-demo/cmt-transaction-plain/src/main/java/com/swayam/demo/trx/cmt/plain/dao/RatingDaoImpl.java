package com.swayam.demo.trx.cmt.plain.dao;

import java.util.List;

import com.swayam.demo.trx.cmt.plain.entity.Rating;

public class RatingDaoImpl implements RatingDao {

//	private final JdbcOperations jdbcOperations;
//
//	public RatingDaoImpl(@Qualifier("mysqlJdbcTemplate") JdbcOperations jdbcOperations) {
//		this.jdbcOperations = jdbcOperations;
//	}

	@Override
	public List<Rating> getRatings() {
//		return jdbcOperations.query("SELECT * FROM RATING", (ResultSet resultSet, int row) -> {
//			return new Rating(resultSet.getLong("id"), resultSet.getString("user_name"), resultSet.getLong("author_id"),
//					resultSet.getInt("rating"));
//		});
		throw new UnsupportedOperationException();
	}

	@Override
	public long addRating(Rating rating) {
//		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
//		String sql = "INSERT INTO RATING (user_name, author_id, rating) VALUES (?, ?, ?)";
//		PreparedStatementCreator psc = (Connection con) -> {
//			PreparedStatement pstat = con.prepareStatement(sql, new String[] { "id" });
//			pstat.setString(1, rating.getUserName());
//			pstat.setLong(2, rating.getAuthorId());
//			pstat.setInt(3, rating.getRating());
//			return pstat;
//		};
//		jdbcOperations.update(psc, generatedKeyHolder);
//		return generatedKeyHolder.getKey().longValue();
		throw new UnsupportedOperationException();
	}

}
