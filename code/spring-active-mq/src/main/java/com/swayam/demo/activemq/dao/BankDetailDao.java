package com.swayam.demo.activemq.dao;

import com.swayam.demo.activemq.model.BankDetailSortOrder;
import com.swayam.demo.activemq.service.QueuePublisher;

public interface BankDetailDao {

    void publishRawBankDetailsAsync(BankDetailSortOrder bankDetailGroups, QueuePublisher dataPublisher);

}