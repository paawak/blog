/*
 * DateEditorSupport.java
 *
 * Created on Sep 4, 2010 11:57:14 PM
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

import java.beans.PropertyEditorSupport;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * 
 * @author paawak
 */
class DateEditorSupport extends PropertyEditorSupport {

    private static final Logger LOG = Logger.getLogger(DateEditorSupport.class);

    private final Format formatter;

    DateEditorSupport(String dateFormat) {
        formatter = new SimpleDateFormat(dateFormat);
    }

    public String getAsText() {

        String date = null;

        Object value = getValue();

        if (value instanceof Date) {

            date = formatter.format(value);

        } else {
            throw new java.lang.IllegalArgumentException("Expecting a "
                    + Date.class.getName() + " class, got "
                    + value.getClass().getName());
        }

        return date;

    }

    public void setAsText(String text) {

        try {

            Date date = (Date) formatter.parseObject(text);
            setValue(date);

        } catch (ParseException e) {
            LOG.fatal("error setting date for String: " + text, e);
        }

    }

}