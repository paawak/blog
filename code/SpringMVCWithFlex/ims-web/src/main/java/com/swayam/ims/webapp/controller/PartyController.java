/*
 * PartyController.java
 *
 * Created on Aug 1, 2009 2:35:17 AM
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

import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.swayam.ims.core.dao.GenericDao;
import com.swayam.ims.model.orm.AccountGroup;
import com.swayam.ims.model.orm.Party;
import com.swayam.ims.model.orm.PartyType;

/**
 * 
 * @author paawak
 */
public class PartyController extends BaseFormController {

    private final GenericDao<AccountGroup, Long> accountGroupDao;
    private final GenericDao<PartyType, Long> partyTypeDao;
    private final GenericDao<Party, Long> partyDao;

    public PartyController(GenericDao<AccountGroup, Long> accountGroupDao,
            GenericDao<PartyType, Long> partyTypeDao,
            GenericDao<Party, Long> partyDao) {
        this.accountGroupDao = accountGroupDao;
        this.partyTypeDao = partyTypeDao;
        this.partyDao = partyDao;

        setCommandName("party");
        setCommandClass(Party.class);

    }

    @Override
    protected void initBinder(HttpServletRequest request,
            ServletRequestDataBinder binder) {

        binder.registerCustomEditor(AccountGroup.class,
                new EntityPropertyEditorSupport<AccountGroup>(accountGroupDao));

        binder.registerCustomEditor(PartyType.class,
                new EntityPropertyEditorSupport<PartyType>(partyTypeDao));

        super.initBinder(request, binder);

    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors)
            throws Exception {

        Party party = (Party) command;

        party.setOpenedOn(new Date());

        partyDao.save(party);

        return new ModelAndView(new RedirectView(getSuccessView()));
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request)
            throws ServletException {
        Party party = new Party();
        return party;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ModelAndView view = super.handleRequest(request, response);

        // set the object to view
        view.addObject("accountGroupList", accountGroupDao.getAll());
        view.addObject("partyTypeList", partyTypeDao.getAll());

        return view;

    }

}
