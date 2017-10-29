package com.swayam.demo.spring.springbootdemo.sql;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

public class DbUnitDataExporter {

	public static void main(String[] args) throws IOException, SQLException, DatabaseUnitException {

		Properties props = new Properties();
		props.load(DbUnitDataExporter.class.getResourceAsStream("/application.properties"));
		String dbUrl = props.getProperty("jdbc.url");
		String dbUser = props.getProperty("jdbc.username");
		String dbPassword = props.getProperty("jdbc.password");

		Connection jdbcConnection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
		IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);
		IDataSet fullDataSet = connection.createDataSet();

		try {
			FlatXmlDataSet.write(fullDataSet, new FileOutputStream("full_data.xml"));
		} finally {
			connection.close();
		}

	}

}
