package com.swayam.demo.stomp.server.service;

import com.swayam.demo.stomp.server.dto.BankDetail;

public interface DataListener {

    void sendMessageToClient(BankDetail bankDetail);

    void endOfMessages();

}