package com.swayam.demo.web.rest.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.swayam.demo.web.rest.model.BankDetail;

public class BankDetailMapper implements RowMapper<BankDetail> {

    @Override
    public BankDetail mapRow(ResultSet resultSet, int row) throws SQLException {
	BankDetail bankDetail = new BankDetail();
	bankDetail.setId(resultSet.getInt("id"));
	bankDetail.setAge(resultSet.getInt("age"));
	bankDetail.setJob(resultSet.getString("job"));
	bankDetail.setMarital(resultSet.getString("marital"));
	bankDetail.setEducation(resultSet.getString("education"));
	bankDetail.setDefaulted(resultSet.getString("defaulted"));
	bankDetail.setBalance(resultSet.getBigDecimal("balance"));
	bankDetail.setHousing(resultSet.getString("housing"));
	bankDetail.setLoan(resultSet.getString("loan"));
	bankDetail.setContact(resultSet.getString("contact"));
	bankDetail.setDay(resultSet.getInt("day"));
	bankDetail.setMonth(resultSet.getString("month"));
	bankDetail.setDuration(resultSet.getInt("duration"));
	bankDetail.setCampaign(resultSet.getInt("campaign"));
	bankDetail.setPdays(resultSet.getInt("pdays"));
	bankDetail.setPrevious(resultSet.getInt("previous"));
	bankDetail.setPoutcome(resultSet.getString("poutcome"));
	bankDetail.setY(resultSet.getString("y"));
	return bankDetail;
    }

}
