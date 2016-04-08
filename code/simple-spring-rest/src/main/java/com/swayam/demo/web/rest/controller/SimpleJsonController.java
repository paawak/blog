package com.swayam.demo.web.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.swayam.demo.web.rest.model.BankDetail;
import com.swayam.demo.web.rest.service.BankDetailService;

@RestController
public class SimpleJsonController {

    private final BankDetailService bankDetailService;

    @Autowired
    public SimpleJsonController(BankDetailService bankDetailService) {
	this.bankDetailService = bankDetailService;
    }

    @RequestMapping(path = { "/test" }, method = { RequestMethod.GET, RequestMethod.POST })
    public BankDetail test() {
	return bankDetailService.getBankDetails().get(0);
    }

}
