package com.swayam.demo.rmi.service.impl;

import java.util.Arrays;
import java.util.List;

import com.swayam.demo.rmi.service.UserService;

public class UserServiceImpl implements UserService {

    @Override
    public List<String> getCurrentUsers() {
        return Arrays.asList("AAA", "BBB", "CCC", "DDD");
    }
}
