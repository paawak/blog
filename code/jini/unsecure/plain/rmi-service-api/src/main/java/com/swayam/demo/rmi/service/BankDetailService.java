package com.swayam.demo.rmi.service;

import java.util.List;
import java.util.Map;

import com.swayam.demo.rmi.dto.BankDetail;
import com.swayam.demo.rmi.dto.BankDetailGroups;

public interface BankDetailService {

    Map<String, List<BankDetail>> getBankDetails(BankDetailGroups group);

}