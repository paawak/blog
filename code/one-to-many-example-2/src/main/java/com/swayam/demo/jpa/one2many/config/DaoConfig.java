package com.swayam.demo.jpa.one2many.config;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.swayam.demo.jpa.one2many.dao.BookDao;
import com.swayam.demo.jpa.one2many.dao.JpaBasedBookDao;
import com.swayam.demo.jpa.one2many.model.Book;

@Configuration
public class DaoConfig {

	@Bean
	public DataSource dataSource(Environment environment) {
		PoolProperties poolProperties = new PoolProperties();
		poolProperties.setUrl(environment.getProperty("jdbc.url"));
		poolProperties.setDriverClassName(environment.getProperty("jdbc.driverClassName"));
		poolProperties.setUsername(environment.getProperty("jdbc.username"));
		poolProperties.setPassword(environment.getProperty("jdbc.password"));
		poolProperties.setJmxEnabled(true);
		poolProperties.setTestWhileIdle(false);
		poolProperties.setTestOnBorrow(true);
		poolProperties.setValidationQuery(environment.getProperty("jdbc.validationQuery"));
		poolProperties.setTestOnReturn(false);
		poolProperties.setValidationInterval(30000);
		poolProperties.setTimeBetweenEvictionRunsMillis(30000);
		poolProperties.setMaxActive(100);
		poolProperties.setInitialSize(10);
		poolProperties.setMaxWait(10000);
		poolProperties.setRemoveAbandonedTimeout(60);
		poolProperties.setMinEvictableIdleTimeMillis(30000);
		poolProperties.setMinIdle(10);
		poolProperties.setLogAbandoned(true);
		poolProperties.setRemoveAbandoned(true);
		poolProperties.setJdbcInterceptors(environment.getProperty("jdbc.interceptors"));

		return new org.apache.tomcat.jdbc.pool.DataSource(poolProperties);
	}

	@Primary
	@Bean
	public BookDao jpaBasedBookDao(EntityManager entityManager) {
		JpaRepository<Book, Long> bookRepository = new SimpleJpaRepository<>(Book.class, entityManager);
		return new JpaBasedBookDao(bookRepository);
	}

}
