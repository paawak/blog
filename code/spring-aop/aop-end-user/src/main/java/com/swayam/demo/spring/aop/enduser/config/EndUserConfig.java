package com.swayam.demo.spring.aop.enduser.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.core.JdbcOperations;

import com.swayam.demo.spring.aop.enduser.dao.BankDetailDao;
import com.swayam.demo.spring.aop.enduser.dao.BankDetailDaoImpl;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan({ "com.swayam.demo.spring.aop.enduser.service", "com.swayam.demo.spring.aop.enduser.logging" })
public class EndUserConfig {

    @Bean
    public BankDetailDao bankDetailDao(@Qualifier("frameworkJdbcTemplate") JdbcOperations jdbcOperations) {
	return new BankDetailDaoImpl(jdbcOperations);
    }

}
