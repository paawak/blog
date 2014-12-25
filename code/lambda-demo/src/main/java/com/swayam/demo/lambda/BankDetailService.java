package com.swayam.demo.lambda;

import java.util.List;
import java.util.Map;

public interface BankDetailService {

	public abstract Map<String, List<BankDetail>> getBankDetails(
			BankDetailGroups group);

}