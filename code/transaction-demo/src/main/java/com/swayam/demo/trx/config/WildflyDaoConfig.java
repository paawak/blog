package com.swayam.demo.trx.config;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jndi.JndiTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

@Profile("wildfly")
@Configuration
public class WildflyDaoConfig implements DaoConfig {

	@Bean
	@Override
	public DataSource mysqlDataSource() throws NamingException {
		return jndiTemplate().lookup("java:/MySqlDSNonXA", DataSource.class);
	}

	@Bean
	@Override
	public DataSource postgresDataSource() throws NamingException {
		return jndiTemplate().lookup("java:/PostgresDSNonXA", DataSource.class);
	}

	@Bean
	@Override
	public PlatformTransactionManager mysqlTxManager(@Qualifier("mysqlDataSource") DataSource dataSource) {
		return new JtaTransactionManager();
	}

	@Bean
	@Override
	public PlatformTransactionManager postgresTxManager(@Qualifier("postgresDataSource") DataSource dataSource) {
		return new JtaTransactionManager();
	}

	private JndiTemplate jndiTemplate() {
		return new JndiTemplate();
	}

}
