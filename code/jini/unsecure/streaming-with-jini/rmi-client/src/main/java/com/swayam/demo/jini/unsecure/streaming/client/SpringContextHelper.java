package com.swayam.demo.jini.unsecure.streaming.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.swayam.demo.jini.unsecure.streaming.api.service.BankDetailService;
import com.swayam.demo.jini.unsecure.streaming.client.config.RmiClientConfig;

public class SpringContextHelper {

    private static final Logger LOG = LoggerFactory.getLogger(SpringContextHelper.class);

    private final BankDetailService bankDetailService;

    public SpringContextHelper() {

	String policyFilePath = SpringContextHelper.class.getResource("/policy.all").getFile();

	LOG.info("Starting with the policy file {}", policyFilePath);

	System.setProperty("java.security.policy", policyFilePath);

	if (System.getSecurityManager() == null) {
	    System.setSecurityManager(new SecurityManager());
	}

	try (ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(RmiClientConfig.class)) {
	    bankDetailService = context.getBean("bankDetailService", BankDetailService.class);
	}

    }

    public BankDetailService getBankDetailService() {
	return bankDetailService;
    }

}
