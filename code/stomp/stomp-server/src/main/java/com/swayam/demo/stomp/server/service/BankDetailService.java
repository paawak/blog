package com.swayam.demo.stomp.server.service;

import com.swayam.demo.stomp.server.dto.BankDetailSortOrder;

public interface BankDetailService {

    void getBankDetailsAsync(BankDetailSortOrder group, DataListener stompListenerForServer);

}