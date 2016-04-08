package com.swayam.demo.web.rest.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.swayam.demo.web.rest.model.BankDetail;

@Repository
public class BankDetailDaoImpl implements BankDetailDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BankDetailDaoImpl(JdbcTemplate jdbcTemplate) {
	this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<BankDetail> getAllBankDetails() {
	return jdbcTemplate.query("select * from bank_details", new BankDetailMapper());
    }

}
