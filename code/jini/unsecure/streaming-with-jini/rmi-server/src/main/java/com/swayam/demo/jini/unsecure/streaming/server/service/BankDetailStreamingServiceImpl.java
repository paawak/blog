package com.swayam.demo.jini.unsecure.streaming.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swayam.demo.jini.unsecure.streaming.api.dto.BankDetail;
import com.swayam.demo.jini.unsecure.streaming.api.service.BankDetailStreamingService;
import com.swayam.demo.jini.unsecure.streaming.api.service.RemoteDataListener;
import com.swayam.demo.jini.unsecure.streaming.server.dao.BankDetailStreamingDao;

@Service
public class BankDetailStreamingServiceImpl implements BankDetailStreamingService {

    private final BankDetailStreamingDao bankDetailStreamingDao;

    @Autowired
    public BankDetailStreamingServiceImpl(BankDetailStreamingDao bankDetailStreamingDao) {
	this.bankDetailStreamingDao = bankDetailStreamingDao;
    }

    @Override
    public void streamAllBankDetails(RemoteDataListener<BankDetail> bankDetailRemoteListener) {
	bankDetailStreamingDao.streamAllBankDetails(bankDetailRemoteListener);
    }

}
