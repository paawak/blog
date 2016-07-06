package com.swayam.demo.spring.aop.enduser.dao;

import java.util.List;

import com.swayam.demo.spring.aop.enduser.model.BankDetail;

public interface BankDetailDao {

    List<BankDetail> getAllBankDetails();

}