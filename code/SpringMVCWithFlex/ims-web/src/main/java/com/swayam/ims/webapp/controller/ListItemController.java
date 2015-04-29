/*
 * ListItemController.java
 *
 * Created on Dec 28, 2009 4:56:59 PM
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.swayam.ims.core.dao.GenericDao;
import com.swayam.ims.model.orm.Item;

/**
 * 
 * @author paawak
 */
public class ListItemController implements Controller {

    private final GenericDao<Item, Long> itemDao;

    public ListItemController(GenericDao<Item, Long> itemDao) {

        this.itemDao = itemDao;

    }

    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // ModelAndView modelView = super.handleRequest(request, response);

        ModelAndView modelView = new ModelAndView("masters/listItem");

        // set the object to view
        modelView.addObject("itemList", itemDao.getAll());

        return modelView;

    }

}
