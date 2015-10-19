package com.swayam.demo.stomp.server.handler.service;

import com.swayam.demo.stomp.server.dto.BankDetailSortOrder;
import com.swayam.demo.stomp.server.handler.StompListenerForServer;

public interface BankDetailService {

    void getBankDetailsAsync(BankDetailSortOrder group,
	    StompListenerForServer stompListenerForServer);

}