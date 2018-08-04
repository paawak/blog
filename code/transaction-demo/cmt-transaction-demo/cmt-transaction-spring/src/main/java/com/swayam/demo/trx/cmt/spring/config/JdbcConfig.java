package com.swayam.demo.trx.cmt.spring.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Configuration
public class JdbcConfig {

	@Bean
	public JdbcOperations mysqlJdbcTemplate(@Qualifier("mysqlDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public JdbcOperations postgresJdbcTemplate(@Qualifier("postgresDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

}
