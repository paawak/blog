package com.swayam.demo.trx.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DaoConfig {

	@Autowired
	private Environment environment;

	@Bean(destroyMethod = "close")
	public DataSource mysqlDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(environment.getProperty("jdbc.mysql.driverClassName"));
		dataSource.setUrl(environment.getProperty("jdbc.mysql.url"));
		dataSource.setUsername(environment.getProperty("jdbc.mysql.username"));
		dataSource.setPassword(environment.getProperty("jdbc.mysql.password"));
		dataSource.setMaxActive(100);
		dataSource.setMaxWait(1000);
		dataSource.setPoolPreparedStatements(true);
		dataSource.setDefaultAutoCommit(false);
		dataSource.setValidationQuery(environment.getProperty("jdbc.mysql.validationQuery"));
		dataSource.setTestOnBorrow(true);
		return dataSource;
	}

	@Bean(destroyMethod = "close")
	public DataSource postgresDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(environment.getProperty("jdbc.postgres.driverClassName"));
		dataSource.setUrl(environment.getProperty("jdbc.postgres.url"));
		dataSource.setUsername(environment.getProperty("jdbc.postgres.username"));
		dataSource.setPassword(environment.getProperty("jdbc.postgres.password"));
		dataSource.setMaxActive(100);
		dataSource.setMaxWait(1000);
		dataSource.setPoolPreparedStatements(true);
		dataSource.setDefaultAutoCommit(false);
		dataSource.setValidationQuery(environment.getProperty("jdbc.postgres.validationQuery"));
		dataSource.setTestOnBorrow(true);
		return dataSource;
	}

	@Bean
	public JdbcTemplate mysqlJdbcTemplate(@Qualifier("mysqlDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public JdbcTemplate postgresJdbcTemplate(@Qualifier("postgresDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

}
