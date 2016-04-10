package com.swayam.demo.activemq.service.pub;

import com.swayam.demo.activemq.model.BankDetail;

public interface QueuePublisher {

    void publish(BankDetail bankDetail);

}