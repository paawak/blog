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

import com.swayam.demo.trx.entity.Rating;

@Repository
public class RatingDaoImpl implements RatingDao {

	private final JdbcTemplate jdbcTemplate;

	public RatingDaoImpl(@Qualifier("mysqlJdbcTemplate") JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Rating> getRatings() {
		return jdbcTemplate.query("SELECT * FROM RATING", (ResultSet resultSet, int row) -> {
			return new Rating(resultSet.getLong("id"), resultSet.getString("user_name"), resultSet.getLong("author_id"),
					resultSet.getInt("rating"));
		});
	}

	@Override
	public long addRating(Rating rating) {
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		String sql = "INSERT INTO RATING (user_name, author_id, rating) VALUES (?, ?, ?)";
		PreparedStatementCreator psc = (Connection con) -> {
			PreparedStatement pstat = con.prepareStatement(sql, new String[] { "id" });
			pstat.setString(1, rating.getUserName());
			pstat.setLong(2, rating.getAuthorId());
			pstat.setInt(3, rating.getRating());
			return pstat;
		};
		jdbcTemplate.update(psc, generatedKeyHolder);
		return generatedKeyHolder.getKey().longValue();
	}

}
