package com.swayam.demo.jini.unsecure.streaming.server.impl;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.swayam.demo.jini.unsecure.streaming.api.dto.BankDetail;
import com.swayam.demo.jini.unsecure.streaming.api.service.RemoteDataListener;

@Repository
public class BankDetailDaoImpl implements BankDetailDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BankDetailDaoImpl(JdbcTemplate jdbcTemplate) {
	this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<BankDetail> getAllBankDetails() {

	return jdbcTemplate.query("select * from bank_details", new RowMapper<BankDetail>() {

	    @Override
	    public BankDetail mapRow(ResultSet resultSet, int row) throws SQLException {
		return mapResultSet(resultSet);
	    }
	});

    }

    @Override
    public void streamAllBankDetails(RemoteDataListener<BankDetail> bankDetailRemoteListener) {

	jdbcTemplate.query("select * from bank_details", new ResultSetExtractor<Void>() {
	    @Override
	    public Void extractData(ResultSet resultSet) throws SQLException, DataAccessException {
		while (resultSet.next()) {
		    try {
			bankDetailRemoteListener.newData(mapResultSet(resultSet));
		    } catch (RemoteException e) {
			throw new RuntimeException(e);
		    }
		}

		try {
		    bankDetailRemoteListener.endOfData();
		} catch (RemoteException e) {
		    throw new RuntimeException(e);
		}
		return null;
	    }
	});

    }

    private BankDetail mapResultSet(ResultSet resultSet) throws SQLException {
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
