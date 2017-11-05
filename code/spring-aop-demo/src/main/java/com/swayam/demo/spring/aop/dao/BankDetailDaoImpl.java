package com.swayam.demo.spring.aop.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import com.swayam.demo.spring.aop.model.BankDetail;

@Repository
public class BankDetailDaoImpl implements BankDetailDao {

	private final JdbcOperations jdbcOperations;

	@Autowired
	public BankDetailDaoImpl(JdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
	}

	@Override
	public List<BankDetail> getAllBankDetails() {
		return jdbcOperations.query("select * from bank_details", new BankDetailMapper());
	}

}
