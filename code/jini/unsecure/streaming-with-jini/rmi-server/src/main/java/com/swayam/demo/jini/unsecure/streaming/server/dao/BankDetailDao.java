package com.swayam.demo.jini.unsecure.streaming.server.dao;

import java.util.List;

import com.swayam.demo.jini.unsecure.streaming.api.dto.BankDetail;

public interface BankDetailDao {

    List<BankDetail> getAllBankDetails();

}