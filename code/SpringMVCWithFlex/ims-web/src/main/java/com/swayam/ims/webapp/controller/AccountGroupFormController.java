/*
 * AccountGroupFormController.java
 *
 * Created on Aug 9, 2009 9:36:24 PM
 *
 * Copyright (c) 2002 - 2009 : Swayam Inc.
 *
 * P R O P R I E T A R Y & C O N F I D E N T I A L
 *
 * The copyright of this document is vested in Swayam Inc. without
 * whose prior written permission its contents must not be published,
 * adapted or reproduced in any form or disclosed or
 * issued to any third party.
 */

package com.swayam.ims.webapp.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.swayam.ims.core.service.impl.AccountGroupManager;
import com.swayam.ims.model.orm.AccountGroup;

/**
 * 
 * @author paawak
 */
public class AccountGroupFormController extends BaseFormController {

    private final AccountGroupManager accountGroupManager;

    public AccountGroupFormController(AccountGroupManager accountGroupManager) {
        this.accountGroupManager = accountGroupManager;

        setCommandName("accountGroup");
        setCommandClass(AccountGroup.class);
    }

    @Override
    protected void initBinder(HttpServletRequest request,
            ServletRequestDataBinder binder) {

        binder.registerCustomEditor(AccountGroup.class,
                new EntityPropertyEditorSupport<AccountGroup>(
                        accountGroupManager.getAccountGroupDao()));

        super.initBinder(request, binder);

    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors)
            throws Exception {

        AccountGroup accountGroup = (AccountGroup) command;

        accountGroupManager.save(accountGroup);

        return new ModelAndView(new RedirectView(getSuccessView()));
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request)
            throws ServletException {
        AccountGroup accountGroup = new AccountGroup();
        return accountGroup;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ModelAndView view = super.handleRequest(request, response);

        // set the object to view
        view.addObject("accountGroupList", accountGroupManager
                .getAccountGroupDao().getAll());

        return view;

    }

}
