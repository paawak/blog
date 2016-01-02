package com.swayam.demo.jini.unsecure.streaming.server.impl;

import java.util.List;

import com.swayam.demo.jini.unsecure.streaming.api.dto.BankDetail;
import com.swayam.demo.jini.unsecure.streaming.api.service.RemoteDataListener;

public interface BankDetailDao {

    List<BankDetail> getAllBankDetails();

    void streamAllBankDetails(RemoteDataListener<BankDetail> bankDetailRemoteListener);

}