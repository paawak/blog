/*
 * ItemController.java
 *
 * Created on Jul 5, 2009 7:25:13 PM
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

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.swayam.ims.core.dao.GenericDao;
import com.swayam.ims.model.orm.Currency;
import com.swayam.ims.model.orm.Item;
import com.swayam.ims.model.orm.ItemGroup;
import com.swayam.ims.model.orm.Unit;

/**
 * 
 * @author paawak
 */
public class ItemController extends BaseFormController {

    private static final Logger LOG = LoggerFactory
            .getLogger(ItemController.class);

    private final GenericDao<Item, Long> itemDao;
    private final GenericDao<ItemGroup, Long> itemGroupDao;
    private final GenericDao<Currency, Long> currencyDao;
    private final GenericDao<Unit, Long> unitDao;

    public ItemController(GenericDao<Item, Long> itemDao,
            GenericDao<ItemGroup, Long> itemGroupDao,
            GenericDao<Currency, Long> currencyDao,
            GenericDao<Unit, Long> unitDao) {

        this.itemDao = itemDao;
        this.itemGroupDao = itemGroupDao;
        this.currencyDao = currencyDao;
        this.unitDao = unitDao;

        setCommandName("item");
        setCommandClass(Item.class);

    }

    @Override
    protected void initBinder(HttpServletRequest request,
            ServletRequestDataBinder binder) {

        binder.registerCustomEditor(ItemGroup.class,
                new EntityPropertyEditorSupport<ItemGroup>(itemGroupDao));

        binder.registerCustomEditor(Currency.class,
                new EntityPropertyEditorSupport<Currency>(currencyDao));

        binder.registerCustomEditor(Unit.class,
                new EntityPropertyEditorSupport<Unit>(unitDao));

        super.initBinder(request, binder);

    }

    @Override
    public ModelAndView onSubmit(Object command) throws ServletException {

        Item item = (Item) command;

        ModelAndView modelView = new ModelAndView(new RedirectView(
                getSuccessView()));

        try {
            // get code, if its blank, set it to null
            String itemCode = item.getCode().trim();

            if ("".equals(itemCode)) {
                item.setCode(null);
            }

            itemDao.save(item);
        } catch (ConstraintViolationException e) {
            LOG.error("Could not save Item", e);
            modelView = new ModelAndView(new RedirectView("dataAccessFailure"));
            modelView.addObject("exception", e);
        }

        return modelView;

    }

    @Override
    protected Object formBackingObject(HttpServletRequest request)
            throws ServletException {
        Item item = new Item();
        return item;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ModelAndView view = super.handleRequest(request, response);

        // set the object to view
        view.addObject("itemGroupList", itemGroupDao.getAll());
        view.addObject("currencyList", currencyDao.getAll());
        view.addObject("unitList", unitDao.getAll());

        return view;

    }

}
