package com.swayam.demo.trx.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@PropertySource("classpath:jdbc.properties")
@Configuration
public class DataAccessConfig {

    @Autowired
    private Environment environment;

    @Bean(destroyMethod = "close")
    public DataSource postgresDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(environment.getProperty("jdbc.postgres.url"));
        dataSource.setUsername(environment.getProperty("jdbc.postgres.username"));
        dataSource.setPassword(environment.getProperty("jdbc.postgres.password"));
        dataSource.setMaxActive(100);
        dataSource.setMaxWait(1000);
        dataSource.setPoolPreparedStatements(true);

        /*
         * FIXME: BAD PRACTICE: auto-commit is true just for this demo, normally
         * auto-commit should ALWAYS be false
         */
        dataSource.setDefaultAutoCommit(true);

        dataSource.setValidationQuery(environment.getProperty("jdbc.postgres.validationQuery"));
        dataSource.setTestOnBorrow(true);
        return dataSource;
    }

    @Bean
    public JdbcOperations postgresJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public PlatformTransactionManager postgresTxManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
