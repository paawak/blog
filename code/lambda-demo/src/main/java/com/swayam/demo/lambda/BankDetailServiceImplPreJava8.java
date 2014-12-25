package com.swayam.demo.lambda;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

public class BankDetailServiceImplPreJava8 implements BankDetailService {

	private final BankDetailDao bankDetailDao;

	public BankDetailServiceImplPreJava8() {
		bankDetailDao = new BankDetailDao();
	}

	@Override
	public Map<String, List<BankDetail>> getBankDetails(BankDetailGroups group) {

		List<BankDetail> unGroupedBankDetails;

		try {
			unGroupedBankDetails = bankDetailDao.getAllBankDetails();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		ListMultimap<String, BankDetail> groupByBroker = ArrayListMultimap
				.create();

		for (BankDetail bankDetail : unGroupedBankDetails) {
			String groupKey = getGroupKey(group, bankDetail);
			groupByBroker.put(groupKey, bankDetail);
		}

		Map<String, List<BankDetail>> groupedBankDetails = new HashMap<>();

		for (String groupKey : groupByBroker.keySet()) {
			List<BankDetail> groupedData = groupByBroker.get(groupKey);
			groupedBankDetails.put(groupKey, groupedData);
		}

		return groupedBankDetails;
	}

	private String getGroupKey(BankDetailGroups group, BankDetail bankDetail) {
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
	}

}
