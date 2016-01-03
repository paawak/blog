package com.swayam.demo.jini.unsecure.streaming.server.dao;

import com.swayam.demo.jini.unsecure.streaming.api.dto.BankDetail;
import com.swayam.demo.jini.unsecure.streaming.api.service.RemoteDataListener;

public interface BankDetailStreamingDao {

    void streamAllBankDetails(RemoteDataListener<BankDetail> bankDetailRemoteListener);

}