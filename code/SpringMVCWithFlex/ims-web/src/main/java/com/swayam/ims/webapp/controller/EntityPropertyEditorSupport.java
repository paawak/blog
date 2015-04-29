/*
 * EntityPropertyEditorSupport.java
 *
 * Created on Jul 5, 2009 8:27:14 PM
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

import java.beans.PropertyEditorSupport;

import com.swayam.ims.core.dao.GenericDao;

/**
 * 
 * @author paawak
 */
public class EntityPropertyEditorSupport<K> extends PropertyEditorSupport {

    private final GenericDao<K, Long> entityDao;

    public EntityPropertyEditorSupport(GenericDao<K, Long> entityDao) {
        this.entityDao = entityDao;
    }

    @Override
    public void setAsText(String text) {

        if (text.trim().equals("") || text.equals("-1")) {
            setSource(null);
            return;
        }

        Long id = Long.valueOf(text);

        K entity = entityDao.get(id);

        setValue(entity);

    }

}
