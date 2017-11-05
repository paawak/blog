package com.swayam.demo.spring.aop;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.swayam.demo.spring.aop.config.DaoConfig;
import com.swayam.demo.spring.aop.config.EndUserConfig;
import com.swayam.demo.spring.aop.model.BankDetail;
import com.swayam.demo.spring.aop.service.BankDetailService;

public class EndUserMain {

	public static void main(String[] a) {

		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DaoConfig.class,
				EndUserConfig.class);) {
			BankDetailService bankDetailService = ctx.getBean(BankDetailService.class);
			List<BankDetail> bankDetails = bankDetailService.getBankDetails();
			System.out.println("EndUserMain.main() : " + bankDetails.size());
		}

	}

}
