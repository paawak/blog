package com.swayam.demo.spring.springbootdemo.sql;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.util.fileloader.DataFileLoader;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;

public class DbUnitDataImporter {

	public static void main(String[] args) throws IOException, SQLException, DatabaseUnitException {

		Properties props = new Properties();
		props.load(DbUnitDataImporter.class.getResourceAsStream("/application.properties"));
		String dbUrl = props.getProperty("jdbc.url");
		String dbUser = props.getProperty("jdbc.username");
		String dbPassword = props.getProperty("jdbc.password");

		Connection jdbcConnection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
		IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);

		DataFileLoader loader = new FlatXmlDataFileLoader();
		IDataSet fullDataSet = loader.loadDataSet(DbUnitDataImporter.class.getResource("/sql/full_data.xml"));

		try {
			DatabaseOperation.CLEAN_INSERT.execute(connection, fullDataSet);
		} finally {
			connection.close();
		}

	}

}
