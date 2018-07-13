package com.swayam.demo.trx.web.rest;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.swayam.demo.trx.entity.BankDetail;
import com.swayam.demo.trx.service.BankDetailService;

@RestController
public class SimpleBankDetailRestController {

	private final BankDetailService bankDetailService;

	public SimpleBankDetailRestController(BankDetailService bankDetailService) {
		this.bankDetailService = bankDetailService;
	}

	@RequestMapping(path = { "/bank-detail" }, method = { RequestMethod.GET })
	public List<BankDetail> getBankDetail() {
		return bankDetailService.getBankDetails();
	}

}
