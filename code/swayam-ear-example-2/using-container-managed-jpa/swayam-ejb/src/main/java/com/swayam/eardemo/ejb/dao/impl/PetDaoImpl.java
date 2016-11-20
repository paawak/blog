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

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swayam.eardemo.ejb.dao.PetDao;
import com.swayam.eardemo.shared.model.Pet;

/**
 * 
 * @author paawak
 */
@Default
@ApplicationScoped
public class PetDaoImpl implements PetDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(PetDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public int save(Pet pet) {
        LOGGER.info("pet: {}", pet);
        entityManager.persist(pet);
        return pet.getId();
    }

}
