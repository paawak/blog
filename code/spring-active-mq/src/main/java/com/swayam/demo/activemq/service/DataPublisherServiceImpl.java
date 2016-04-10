package com.swayam.demo.activemq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swayam.demo.activemq.dao.BankDetailDao;
import com.swayam.demo.activemq.model.BankDetailSortOrder;

@Service
public class DataPublisherServiceImpl implements DataPublisherService {

    private final DataPublisher dataPublisher;
    private final BankDetailDao bankDetailDao;

    @Autowired
    public DataPublisherServiceImpl(DataPublisher dataPublisher, BankDetailDao bankDetailDao) {
	this.dataPublisher = dataPublisher;
	this.bankDetailDao = bankDetailDao;
    }

    @Override
    public void publishData(BankDetailSortOrder bankDetailGroups) {
	bankDetailDao.publishRawBankDetailsAsync(bankDetailGroups, dataPublisher);
    }

}
