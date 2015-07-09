/*
 * AbstractTimestampTest.java
 *
 * Created on Nov 2, 2010 12:28:50 AM
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

package com.swayam.demo.oracle.hibernate.test;

import static com.swayam.demo.oracle.hibernate.util.DateUtil.getTimeWithZone;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.swayam.demo.oracle.hibernate.entity.ITimestampDemo;
import com.swayam.demo.oracle.hibernate.util.HibernateUtil;

/**
 * 
 * @author paawak
 */
public abstract class AbstractTimestampTest {

    private final Class<?> entity;

    protected AbstractTimestampTest(Class<?> entity) {
        this.entity = entity;
    }

    protected Session session;

    @Before
    public void setUp() throws Exception {
        session = HibernateUtil.getSession();
    }

    @Test
    public void testInsert() throws InstantiationException,
            IllegalAccessException {

        Calendar cal = getTimeWithZone();

        ITimestampDemo demo = (ITimestampDemo) entity.newInstance();
        demo.setName(entity.getSimpleName());
        demo.setTimeWithZone(cal);
        demo.setTimeWithZoneLocal(cal);

        Transaction trx = session.beginTransaction();
        session.save(demo);
        trx.commit();

    }

    @Test
    @SuppressWarnings("unchecked")
    public void testSelect() {

        Query query = session.createQuery("FROM " + entity.getName());

        List<ITimestampDemo> list = query.list();

        for (ITimestampDemo demo : list) {

            System.out.println(demo.getName() + ": " + demo.getId() + ", TIME="
                    + getTimeWithZone(demo.getTimeWithZone()) + ", TIME_LOCAL="
                    + getTimeWithZone(demo.getTimeWithZoneLocal()));

        }

    }

    @After
    public void tearDown() throws Exception {
        session.close();
    }

}
