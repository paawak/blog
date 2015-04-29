/*
 * ItemValidator.java
 *
 * Created on Sep 5, 2010 12:01:26 AM
 *
 * Copyright (c) 2002 - 2008 : Swayam Inc.
 *
 * P R O P R I E T A R Y & C O N F I D E N T I A L
 *
 * The copyright of this document is vested in Swayam Inc. without
 * whose prior written permission its contents must not be published,
 * adapted or reproduced in any form or disclosed or
 * issued to any third party.
 */

package com.swayam.demo.web.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.swayam.demo.web.formbean.ItemBean;

/**
 * 
 * @author paawak
 */
class ItemValidator implements Validator {

    @Override
    public void validate(Object target, Errors errors) {

        ItemBean bean = (ItemBean) target;

        if (bean.getTotalPrice() == 0) {
            errors.rejectValue("totalPrice", "noItemsSelected");
        }

    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == ItemBean.class;
    }

}
