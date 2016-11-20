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

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swayam.eardemo.ejb.dao.PersonDao;
import com.swayam.eardemo.shared.model.Person;

/**
 * 
 * @author paawak
 */
@Default
@ApplicationScoped
public class PersonDaoImpl implements PersonDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Resource(lookup = "java:/datasources/SwayamEarDS")
    private DataSource dataSource;

    @Override
    public int saveWithEntityManager(Person person) {
        LOGGER.info("person: {}", person);
        entityManager.persist(person);
        return person.getId();
    }

    @Override
    public int saveWithConnection(Person person) {
        try {
            Connection con = dataSource.getConnection();
            String sql = "insert into person (first_name, last_name, date_of_birth) values (?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getLastName());
            preparedStatement.setDate(3, Date.valueOf(person.getDateOfBirth()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

}
