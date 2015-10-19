package com.swayam.demo.stomp.server.service;

import com.swayam.demo.stomp.server.dto.BankDetail;

public interface StompListenerForServer {

    void sendMessageToServer(BankDetail bankDetail);

    void endOfMessages();

}