package com.swayam.demo.activemq.service.pub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swayam.demo.activemq.dao.BankDetailDao;
import com.swayam.demo.activemq.model.BankDetailSortOrder;

@Service
public class DataPublisherServiceImpl implements DataPublisherService {

    private final QueuePublisher dataPublisher;
    private final BankDetailDao bankDetailDao;

    @Autowired
    public DataPublisherServiceImpl(QueuePublisher dataPublisher, BankDetailDao bankDetailDao) {
	this.dataPublisher = dataPublisher;
	this.bankDetailDao = bankDetailDao;
    }

    @Override
    public void publishData(BankDetailSortOrder bankDetailGroups) {
	bankDetailDao.publishRawBankDetailsAsync(bankDetailGroups, dataPublisher);
    }

}
