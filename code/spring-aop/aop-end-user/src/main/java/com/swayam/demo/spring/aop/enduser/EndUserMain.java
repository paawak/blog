package com.swayam.demo.spring.aop.enduser;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.swayam.demo.spring.aop.enduser.config.EndUserConfig;
import com.swayam.demo.spring.aop.enduser.model.BankDetail;
import com.swayam.demo.spring.aop.enduser.service.BankDetailService;
import com.swayam.demo.spring.aop.framework.config.DaoConfig;

public class EndUserMain {

    public static void main(String[] a) {

	try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DaoConfig.class, EndUserConfig.class);) {
	    BankDetailService bankDetailService = ctx.getBean(BankDetailService.class);
	    List<BankDetail> bankDetails = bankDetailService.getBankDetails();
	    System.out.println("EndUserMain.main() : " + bankDetails.size());
	}

    }

}
