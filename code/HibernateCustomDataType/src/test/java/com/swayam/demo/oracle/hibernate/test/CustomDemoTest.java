/*
 * CustomTestTest.java
 *
 * Created on Oct 31, 2010 5:33:27 PM
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

import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;

import com.swayam.demo.oracle.hibernate.CustomDemo;
import com.swayam.demo.oracle.hibernate.HibernateUtil;

/**
 * 
 * @author paawak
 */
public class CustomDemoTest {

    private Session session;

    @Before
    public void init() {
        session = HibernateUtil.getSession();
    }

    @Test
    public void insert() {

        CustomDemo ct = new CustomDemo();
        ct.setName("nonameo");
        ct.setTimeWithZone(new GregorianCalendar());

        Transaction tr = session.beginTransaction();
        session.save(ct);
        tr.commit();

    }

    @SuppressWarnings("unchecked")
    @Test
    public void select() {

        Query query = session.createQuery("from " + CustomDemo.class.getName());

        List<CustomDemo> cts = query.list();

        for (CustomDemo ct : cts) {
            System.out.println("id=" + ct.getId() + ", name=" + ct.getName()
                    + ", time=" + ct.getTimeWithZone());
        }

    }

}
