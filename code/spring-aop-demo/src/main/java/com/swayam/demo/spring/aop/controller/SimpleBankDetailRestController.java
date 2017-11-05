package com.swayam.demo.spring.aop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.swayam.demo.spring.aop.model.BankDetail;
import com.swayam.demo.spring.aop.service.BankDetailService;

@RestController
public class SimpleBankDetailRestController {

	private final BankDetailService bankDetailService;

	@Autowired
	public SimpleBankDetailRestController(BankDetailService bankDetailService) {
		this.bankDetailService = bankDetailService;
	}

	@RequestMapping(path = { "/bank-detail" }, method = { RequestMethod.GET })
	public BankDetail getBankDetail() {
		return bankDetailService.getBankDetails().get(0);
	}

}
