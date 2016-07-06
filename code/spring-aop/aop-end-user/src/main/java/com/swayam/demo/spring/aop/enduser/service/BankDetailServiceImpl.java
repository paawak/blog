package com.swayam.demo.spring.aop.enduser.service;

import java.util.List;

import com.swayam.demo.spring.aop.enduser.dao.BankDetailDao;
import com.swayam.demo.spring.aop.enduser.model.BankDetail;

public class BankDetailServiceImpl implements BankDetailService {

    private final BankDetailDao bankDetailDao;

    public BankDetailServiceImpl(BankDetailDao bankDetailDao) {
	this.bankDetailDao = bankDetailDao;
    }

    @Override
    public List<BankDetail> getBankDetails() {
	return bankDetailDao.getAllBankDetails();
    }

}
