package com.swayam.demo.rmi.server.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.swayam.demo.rmi.dto.BankDetail;
import com.swayam.demo.rmi.dto.BankDetailGroups;
import com.swayam.demo.rmi.service.BankDetailService;

public class BankDetailServiceImplJava8 implements BankDetailService {

    private final BankDetailDao bankDetailDao;

    public BankDetailServiceImplJava8() {
        bankDetailDao = new BankDetailDao();
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

		Collector<BankDetail, ?, Map<String, List<BankDetail>>> groupByCollector = Collectors
				.groupingBy(groupByClassifier);

		Map<String, List<BankDetail>> groupedBankDetails;
		List<BankDetail> unGroupedBankDetails;

		try {
			unGroupedBankDetails = bankDetailDao.getAllBankDetails();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		groupedBankDetails = unGroupedBankDetails.parallelStream().collect(
				groupByCollector);

		return groupedBankDetails;
	}
}
