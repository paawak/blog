package com.swayam.demo.rmi.client;

import java.rmi.RMISecurityManager;

import net.jini.core.discovery.LookupLocator;
import net.jini.core.entry.Entry;
import net.jini.core.lookup.ServiceRegistrar;
import net.jini.core.lookup.ServiceTemplate;
import net.jini.lookup.entry.Name;

import com.swayam.demo.rmi.service.BankAccountService;
import com.swayam.demo.rmi.service.UserService;

public class SimpleNonSecureRmiClient {

	/**
	 * Needs to be run with the following JVM args:<br/>
	 * <ul>
	 * 	<li>-Djava.security.policy=/full-path/RmiClient/src/main/resources/policy.all</li>
	 * 	<li>-Djava.rmi.server.RMIClassLoaderSpi=net.jini.loader.pref.PreferredClassProvider</li>
	 * </ul>
	 */
    public static void main(String[] args) throws Exception {
    	System.setSecurityManager(new RMISecurityManager());
        LookupLocator lookup = new LookupLocator("jini://localhost:4160");

        ServiceRegistrar registrar = lookup.getRegistrar();
        
        //give the poor guys some time to lookup
		Thread.sleep(10); 

        UserService userService = (UserService) registrar.lookup(new ServiceTemplate(null,
                new Class[] { UserService.class }, new Entry[] { new Name(UserService.class.getSimpleName()) }));
        System.out.println("Getting current users from remote: " + userService.getCurrentUsers());

        BankAccountService bankAccountService = (BankAccountService) registrar.lookup(new ServiceTemplate(null,
                new Class[] { BankAccountService.class }, new Entry[] { new Name(BankAccountService.class
                        .getSimpleName()) }));
        System.out.println("Creating bank account from remote, id: " + bankAccountService.createBankAccount("aaa"));

    }

}
