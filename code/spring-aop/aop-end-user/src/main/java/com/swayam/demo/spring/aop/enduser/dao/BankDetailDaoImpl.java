package com.swayam.demo.spring.aop.enduser.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcOperations;

import com.swayam.demo.spring.aop.enduser.model.BankDetail;

public class BankDetailDaoImpl implements BankDetailDao {

    private final JdbcOperations jdbcOperations;

    public BankDetailDaoImpl(JdbcOperations jdbcTemplate) {
	this.jdbcOperations = jdbcTemplate;
    }

    @Override
    public List<BankDetail> getAllBankDetails() {
	return jdbcOperations.query("select * from bank_details", new BankDetailMapper());
    }

}
