package com.swayam.demo.stomp.server.service;

import com.swayam.demo.stomp.server.dto.BankDetailGroups;
import com.swayam.demo.stomp.server.stomp.StompListenerForServer;

public interface BankDetailService {

    void getBankDetailsAsync(BankDetailGroups group,
	    StompListenerForServer stompListenerForServer);

}