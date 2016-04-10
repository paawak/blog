package com.swayam.demo.activemq.service;

import com.swayam.demo.activemq.model.BankDetail;

public interface DataPublisher {

    void publish(BankDetail bankDetail);

}