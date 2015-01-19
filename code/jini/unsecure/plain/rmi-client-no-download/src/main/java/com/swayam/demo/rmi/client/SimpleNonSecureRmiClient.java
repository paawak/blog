package com.swayam.demo.rmi.client;

import net.jini.core.discovery.LookupLocator;
import net.jini.core.entry.Entry;
import net.jini.core.lookup.ServiceRegistrar;
import net.jini.core.lookup.ServiceTemplate;
import net.jini.lookup.entry.Name;

import com.swayam.demo.rmi.service.BankAccountService;
import com.swayam.demo.rmi.service.UserService;

public class SimpleNonSecureRmiClient {

    public static void main(String[] args) throws Exception {
        System.setProperty("java.security.policy", System.getProperty("user.home") + "/jini/policy.all");

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
            System.out.println("DynamicDownloadRmiClient.main() setting custom security manager");
        }

        LookupLocator lookup = new LookupLocator("jini://localhost:4160");

        ServiceRegistrar registrar = lookup.getRegistrar();

        // give the poor guys some time to lookup
        Thread.sleep(10);

        UserService userService = (UserService) registrar.lookup(new ServiceTemplate(null, new Class[] { UserService.class }, new Entry[] { new Name(UserService.class
                .getSimpleName()) }));
        System.out.println("Getting current users from remote: " + userService.getCurrentUsers());

        BankAccountService bankAccountService = (BankAccountService) registrar.lookup(new ServiceTemplate(null, new Class[] { BankAccountService.class }, new Entry[] { new Name(
                BankAccountService.class.getSimpleName()) }));
        System.out.println("Creating bank account from remote, id: " + bankAccountService.createBankAccount("aaa"));

    }

}
