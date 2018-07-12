package com.swayam.demo.trx.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.swayam.demo.trx.model.BankDetail;

@Repository
public class BankDetailDaoImpl implements BankDetailDao {

	private final JdbcTemplate jdbcTemplate;

	public BankDetailDaoImpl(@Qualifier("mysqlJdbcTemplate") JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<BankDetail> getAllBankDetails() {
		return jdbcTemplate.query("select * from bank_details", new BankDetailMapper());
	}

}
