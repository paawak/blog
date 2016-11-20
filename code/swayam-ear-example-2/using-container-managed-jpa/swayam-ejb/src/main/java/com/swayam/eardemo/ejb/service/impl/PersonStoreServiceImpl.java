/*
 * PersonServiceImpl.java
 *
 * Created on 18-Nov-2016 1:09:09 AM
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

package com.swayam.eardemo.ejb.service.impl;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swayam.eardemo.ejb.dao.PersonDao;
import com.swayam.eardemo.ejb.dao.PetDao;
import com.swayam.eardemo.ejb.service.PetStoreService;
import com.swayam.eardemo.shared.model.Person;
import com.swayam.eardemo.shared.model.Pet;

/**
 * 
 * @author paawak
 */
@Default
@ApplicationScoped
public class PersonStoreServiceImpl implements PetStoreService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonStoreServiceImpl.class);

    private final PersonDao personDao;
    private final PetDao petDao;

    @Inject
    public PersonStoreServiceImpl(PersonDao personDao, PetDao petDao) {
        this.personDao = personDao;
        this.petDao = petDao;
    }

    @Override
    public int save(Person person, Pet pet) {
        LOGGER.info("saving person...");
        int personId = personDao.save(person);

        LOGGER.info("saved person, returned id is: {}", personId);

        LOGGER.info("saving pet...");

        int petId = petDao.save(pet);

        LOGGER.info("saved pet, returned id is: {}", petId);

        return personId;
    }

}
