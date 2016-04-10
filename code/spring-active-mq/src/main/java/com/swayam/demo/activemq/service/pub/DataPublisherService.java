package com.swayam.demo.activemq.service.pub;

import com.swayam.demo.activemq.model.BankDetailSortOrder;

public interface DataPublisherService {

    void publishData(BankDetailSortOrder bankDetailGroups);

}
