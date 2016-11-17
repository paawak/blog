/*
 * PersonDaoImpl.java
 *
 * Created on 18-Nov-2016 1:09:51 AM
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

package com.swayam.eardemo.ejb.dao.impl;

import org.springframework.stereotype.Repository;

import com.swayam.eardemo.ejb.dao.PersonDao;
import com.swayam.eardemo.shared.model.Person;

/**
 * 
 * @author paawak
 */
@Repository
public class PersonDaoImpl implements PersonDao {

    @Override
    public int save(Person person) {
        // TODO Auto-generated method stub
        return 0;
    }

}
