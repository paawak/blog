package com.swayam.demo.stomp.server.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DaoConfig {

    @Autowired
    private Environment environment;

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
	BasicDataSource dataSource = new BasicDataSource();
	dataSource.setDriverClassName(environment.getProperty("jdbc.driverClassName"));
	dataSource.setUrl(environment.getProperty("jdbc.url"));
	dataSource.setUsername(environment.getProperty("jdbc.username"));
	dataSource.setPassword(environment.getProperty("jdbc.password"));
	dataSource.setMaxActive(100);
	dataSource.setMaxWait(1000);
	dataSource.setPoolPreparedStatements(true);
	dataSource.setDefaultAutoCommit(false);
	dataSource.setValidationQuery(environment.getProperty("jdbc.validationQuery"));
	dataSource.setTestOnBorrow(true);
	return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
	return new JdbcTemplate(dataSource);
    }

}
