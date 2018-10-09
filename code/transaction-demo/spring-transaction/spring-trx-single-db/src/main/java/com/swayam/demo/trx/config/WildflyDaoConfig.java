package com.swayam.demo.trx.config;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jndi.JndiTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

@Profile("wildfly")
@Configuration
public class WildflyDaoConfig {

	@Bean
	public DataSource mysqlDataSource(JndiTemplate jndiTemplate) throws NamingException {
		return jndiTemplate.lookup("java:/MySqlDSNonXA", DataSource.class);
	}

	@Bean
	public DataSource postgresDataSource(JndiTemplate jndiTemplate) throws NamingException {
		return jndiTemplate.lookup("java:/PostgresDSNonXA", DataSource.class);
	}

	@Bean(name = { "postgresTxManager", "mysqlTxManager" })
	public PlatformTransactionManager containerManagedTxManager() {
		return new JtaTransactionManager();
	}

	@Bean
	public JndiTemplate jndiTemplate() {
		return new JndiTemplate();
	}

}
