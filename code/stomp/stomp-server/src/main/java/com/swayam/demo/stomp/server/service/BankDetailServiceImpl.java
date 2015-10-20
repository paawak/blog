package com.swayam.demo.stomp.server.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swayam.demo.stomp.server.dao.BankDetailDao;
import com.swayam.demo.stomp.server.dto.BankDetailSortOrder;

@Service("bankDetailServiceImpl")
public class BankDetailServiceImpl implements BankDetailService {

    private final BankDetailDao bankDetailDao;

    @Autowired
    public BankDetailServiceImpl(BankDetailDao bankDetailDao) {
	this.bankDetailDao = bankDetailDao;
    }

    @Override
    public void getBankDetailsAsync(BankDetailSortOrder group, DataListener stompListenerForServer) {

	try {
	    bankDetailDao.getBankDetailsAsync(group, stompListenerForServer);
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}

    }

}
