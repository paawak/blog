package com.swayam.demo.lambda;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class BankDetailService {

	private final BankDetailDao bankDetailDao;

	public BankDetailService() {
		bankDetailDao = new BankDetailDao();
	}

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

		try {
			groupedBankDetails = bankDetailDao.getAllBankDetails()
					.parallelStream().collect(groupByCollector);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return groupedBankDetails;
	}
}
