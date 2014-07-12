package com.swayam.demo.rmi.client;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.swayam.demo.rmi.service.BankAccountService;
import com.swayam.demo.rmi.service.UserService;

public class SpringNonSecureRmiClient {

	/**
	 * Needs to be run with the following JVM args:<br/>
	 * <ul>
	 * <li>
	 * -Djava.security.policy=/full-path/RmiClient/src/main/resources/policy.all
	 * </li>
	 * <li>-Djava.rmi.server.RMIClassLoaderSpi=net.jini.loader.pref.
	 * PreferredClassProvider</li>
	 * </ul>
	 */
	public static void main(String[] args) throws Exception {
		try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"client-application.xml")) {
			UserService userService = (UserService) context
					.getBean("userService");
			System.out.println("Getting current users from remote: "
					+ userService.getCurrentUsers());

			BankAccountService bankAccountService = (BankAccountService) context
					.getBean("bankAccountService");
			System.out.println("Creating bank account from remote, id: "
					+ bankAccountService.createBankAccount("aaa"));
		}

	}

}
