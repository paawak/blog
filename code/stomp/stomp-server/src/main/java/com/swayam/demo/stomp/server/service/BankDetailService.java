package com.swayam.demo.stomp.server.service;

import com.swayam.demo.stomp.server.dto.BankDetailSortOrder;
import com.swayam.demo.stomp.server.stomp.impl.StompListenerForServer;

public interface BankDetailService {

    void getBankDetailsAsync(BankDetailSortOrder group,
	    StompListenerForServer stompListenerForServer);

}