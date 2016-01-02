package com.swayam.demo.jini.unsecure.streaming.server.impl;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swayam.demo.jini.unsecure.streaming.api.dto.BankDetail;
import com.swayam.demo.jini.unsecure.streaming.api.dto.BankDetailGroups;
import com.swayam.demo.jini.unsecure.streaming.api.service.BankDetailService;
import com.swayam.demo.jini.unsecure.streaming.api.service.RemoteDataListener;

@Service("bankDetailServiceImpl")
public class BankDetailServiceImpl implements BankDetailService {

    private final BankDetailDao bankDetailDao;

    @Autowired
    public BankDetailServiceImpl(BankDetailDao bankDetailDao) {
	this.bankDetailDao = bankDetailDao;
    }

    @Override
    public Map<String, List<BankDetail>> getBankDetails(BankDetailGroups group) {

	Function<BankDetail, String> groupByClassifier = (BankDetail bankDetail) -> {
	    switch (group) {
	    case JOB:
		return bankDetail.getJob();
	    case EDUCATION:
		return bankDetail.getEducation();
	    case MARITAL_STATUS:
		return bankDetail.getMarital();
	    default:
		throw new IllegalArgumentException();
	    }
	};

	Collector<BankDetail, ?, Map<String, List<BankDetail>>> groupByCollector = Collectors.groupingBy(groupByClassifier);

	Map<String, List<BankDetail>> groupedBankDetails;
	List<BankDetail> unGroupedBankDetails;

	try {
	    unGroupedBankDetails = bankDetailDao.getAllBankDetails();
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}

	groupedBankDetails = unGroupedBankDetails.parallelStream().collect(groupByCollector);

	return groupedBankDetails;
    }

    @Override
    public void streamAllBankDetails(RemoteDataListener<BankDetail> bankDetailRemoteListener) {
	try {
	    bankDetailDao.streamAllBankDetails(bankDetailRemoteListener);
	} catch (SQLException | RemoteException e) {
	    throw new RuntimeException(e);
	}
    }

}
