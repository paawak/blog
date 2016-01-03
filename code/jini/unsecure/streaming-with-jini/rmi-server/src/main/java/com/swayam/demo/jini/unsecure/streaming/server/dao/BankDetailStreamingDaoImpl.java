package com.swayam.demo.jini.unsecure.streaming.server.dao;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.swayam.demo.jini.unsecure.streaming.api.dto.BankDetail;
import com.swayam.demo.jini.unsecure.streaming.api.service.RemoteDataListener;

@Repository
public class BankDetailStreamingDaoImpl implements BankDetailStreamingDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BankDetailStreamingDaoImpl(JdbcTemplate jdbcTemplate) {
	this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void streamAllBankDetails(RemoteDataListener<BankDetail> bankDetailRemoteListener) {

	RowMapper<BankDetail> rowMapper = new BankDetailMapper();

	jdbcTemplate.query("select * from bank_details", new ResultSetExtractor<Void>() {
	    @Override
	    public Void extractData(ResultSet resultSet) throws SQLException, DataAccessException {
		while (resultSet.next()) {
		    try {
			bankDetailRemoteListener.newData(rowMapper.mapRow(resultSet, -1));
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

}
