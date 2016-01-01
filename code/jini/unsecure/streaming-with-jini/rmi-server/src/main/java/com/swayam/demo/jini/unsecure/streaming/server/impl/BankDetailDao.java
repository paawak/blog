package com.swayam.demo.jini.unsecure.streaming.server.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.swayam.demo.jini.unsecure.streaming.api.dto.BankDetail;

@Repository
public class BankDetailDao {

    public List<BankDetail> getAllBankDetails() throws SQLException {

        String mysqlConnectionString = "jdbc:mysql://localhost/datasets?createDatabaseIfNotExist=true&amp;amp;useUnicode=true&amp;amp;characterEncoding=utf-8&amp;amp;autoReconnect=true";
        List<BankDetail> bankDetails = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(mysqlConnectionString, "root", "root123");
                PreparedStatement pStat = connection.prepareStatement("select * from bank_details");
                ResultSet resultSet = pStat.executeQuery();) {

            while (resultSet.next()) {
                bankDetails.add(mapResultSet(resultSet));
            }
        }

        return bankDetails;

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
