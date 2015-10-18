package com.swayam.demo.stomp.server.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.swayam.demo.stomp.server.dto.BankDetail;
import com.swayam.demo.stomp.server.dto.BankDetailSortOrder;
import com.swayam.demo.stomp.server.stomp.impl.StompListenerForServer;

@Repository
public class BankDetailDao {

    public void getBankDetailsAsync(BankDetailSortOrder bankDetailGroups,
	    StompListenerForServer stompListenerForServer) throws SQLException {

	// Tomcat 8 needs this for some weird reason
	try {
	    Class.forName("com.mysql.jdbc.Driver").newInstance();
	} catch (InstantiationException | IllegalAccessException
		| ClassNotFoundException e) {
	    throw new RuntimeException("error loading mysql driver", e);
	}

	String mysqlConnectionString = "jdbc:mysql://localhost/datasets?createDatabaseIfNotExist=true&amp;amp;useUnicode=true&amp;amp;characterEncoding=utf-8&amp;amp;autoReconnect=true";

	try (Connection connection = DriverManager.getConnection(
		mysqlConnectionString, "root", "root123");
		PreparedStatement pStat = connection
			.prepareStatement("select * from bank_details order by ?");) {

	    pStat.setString(1, bankDetailGroups.getColumnName());

	    try (ResultSet resultSet = pStat.executeQuery();) {

		while (resultSet.next()) {
		    stompListenerForServer
			    .sendMessageToServer(mapResultSet(resultSet));
		}
	    }

	    stompListenerForServer.endOfMessages();
	}

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
