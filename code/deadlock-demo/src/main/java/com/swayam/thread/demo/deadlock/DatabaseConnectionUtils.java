package com.swayam.thread.demo.deadlock;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

public class DatabaseConnectionUtils {

    public static final DatabaseConnectionUtils INSTANCE = new DatabaseConnectionUtils();

    private final DataSource dataSource;

    private DatabaseConnectionUtils() {
        dataSource = newDataSource();
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private DataSource newDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/deadlock_demo");
        dataSource.setUsername("root");
        dataSource.setPassword("root123");
        dataSource.setMaxActive(100);
        dataSource.setMaxWait(1000);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setDefaultAutoCommit(true);
        dataSource.setValidationQuery("SELECT 1 + 1");
        dataSource.setTestOnBorrow(true);
        return dataSource;
    }

}
