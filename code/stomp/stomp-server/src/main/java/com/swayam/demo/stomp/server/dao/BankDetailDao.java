package com.swayam.demo.stomp.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.swayam.demo.stomp.server.dto.BankDetail;
import com.swayam.demo.stomp.server.dto.BankDetailSortOrder;
import com.swayam.demo.stomp.server.service.DataListener;

@Repository
public class BankDetailDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BankDetailDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void getBankDetailsAsync(BankDetailSortOrder bankDetailGroups, DataListener stompListenerForServer) throws SQLException {

        ResultSetExtractor<Void> resultSetExtractor = new ResultSetExtractor<Void>() {
            @Override
            public Void extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                while (resultSet.next()) {
                    stompListenerForServer.sendMessageToClient(mapResultSet(resultSet));
                }
                stompListenerForServer.endOfMessages();
                return null;
            }
        };

        jdbcTemplate.query("select * from bank_details order by ?", new Object[] { bankDetailGroups.getColumnName() }, resultSetExtractor);

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
