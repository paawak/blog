package com.swayam.demo.trx.config;

import javax.sql.DataSource;

import org.springframework.transaction.PlatformTransactionManager;

public interface DaoConfig {

	DataSource mysqlDataSource() throws Exception;

	DataSource postgresDataSource() throws Exception;

	PlatformTransactionManager mysqlTxManager(DataSource dataSource);

	PlatformTransactionManager postgresTxManager(DataSource dataSource);

}