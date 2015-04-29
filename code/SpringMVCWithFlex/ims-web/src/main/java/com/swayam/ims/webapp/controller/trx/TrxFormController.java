/*
 * TrxFormController.java
 *
 * Created on Jan 9, 2010 8:22:32 PM
 *
 * Copyright (c) 2002 - 2010 : Swayam Inc.
 *
 * P R O P R I E T A R Y & C O N F I D E N T I A L
 *
 * The copyright of this document is vested in Swayam Inc. without
 * whose prior written permission its contents must not be published,
 * adapted or reproduced in any form or disclosed or
 * issued to any third party.
 */

package com.swayam.ims.webapp.controller.trx;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * 
 * @author paawak
 */
public abstract class TrxFormController implements Controller {

    final TrxModeIndicator modeIndicator;

    private String formView;

    public TrxFormController(TrxModeIndicator modeIndicator) {
        this.modeIndicator = modeIndicator;
    }

    public final void setFormView(String formView) {
        this.formView = formView;
    }

    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ModelAndView modelNView = new ModelAndView(formView);

        modelNView.addObject("isPurchaseMode", modeIndicator.isPurchaseMode());

        return modelNView;

    }

}
