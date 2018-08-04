package com.swayam.demo.trx.cmt.spring.config;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jndi.JndiTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

@Configuration
@PropertySource("classpath:jdbc.properties")
public class WildflyDaoConfig {

	@Autowired
	private Environment environment;

	@Bean
	public DataSource mysqlDataSource(JndiTemplate jndiTemplate) throws NamingException {
		return jndiTemplate.lookup(environment.getProperty("jndi.jdbc.datasource.mysql.nonxa"), DataSource.class);
	}

	@Bean
	public DataSource postgresDataSource(JndiTemplate jndiTemplate) throws NamingException {
		return jndiTemplate.lookup(environment.getProperty("jndi.jdbc.datasource.postgres.nonxa"), DataSource.class);
	}

	@Bean
	public PlatformTransactionManager containerManagedTxManager() {
		return new JtaTransactionManager();
	}

	@Bean
	public JndiTemplate jndiTemplate() {
		return new JndiTemplate();
	}

}
