package com.swayam.demo.rmi.server.impl;

import java.util.Random;

import com.swayam.demo.rmi.service.BankAccountService;

public class BankAccountServiceImpl implements BankAccountService {

    private final Random random = new Random(System.currentTimeMillis());

    @Override
    public long createBankAccount(String userName) {
        System.out.println("BankAccountServiceImpl.createBankAccount(): creating bank account for user: " + userName);
        return random.nextLong();
    }

}
