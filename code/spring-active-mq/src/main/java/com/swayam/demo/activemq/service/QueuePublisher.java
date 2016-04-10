package com.swayam.demo.activemq.service;

import com.swayam.demo.activemq.model.BankDetail;

public interface QueuePublisher {

    void publish(BankDetail bankDetail);

}