package com.swayam.demo.spring.aop.enduser.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;

import com.swayam.demo.spring.aop.enduser.dao.BankDetailDao;
import com.swayam.demo.spring.aop.enduser.dao.BankDetailDaoImpl;
import com.swayam.demo.spring.aop.enduser.service.BankDetailService;
import com.swayam.demo.spring.aop.enduser.service.BankDetailServiceImpl;

@Configuration
public class EndUserConfig {

    @Bean
    public BankDetailDao bankDetailDao(@Qualifier("frameworkJdbcTemplate") JdbcOperations jdbcOperations) {
	return new BankDetailDaoImpl(jdbcOperations);
    }

    @Bean
    public BankDetailService bankDetailService(BankDetailDao bankDetailDao) {
	return new BankDetailServiceImpl(bankDetailDao);
    }

}
