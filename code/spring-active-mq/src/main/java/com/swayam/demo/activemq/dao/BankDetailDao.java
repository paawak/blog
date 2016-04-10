package com.swayam.demo.activemq.dao;

import com.swayam.demo.activemq.model.BankDetailSortOrder;
import com.swayam.demo.activemq.service.DataPublisher;

public interface BankDetailDao {

    void publishRawBankDetailsAsync(BankDetailSortOrder bankDetailGroups, DataPublisher dataPublisher);

}