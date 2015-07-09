/*
 * HibernateUtil.java
 *
 * Created on Sep 12, 2010 7:07:41 PM
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

package com.swayam.demo.oracle.hibernate.util;

import org.hibernate.HibernateException;
import org.hibernate.Interceptor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * 
 * @author paawak
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {

        try {

            sessionFactory = new AnnotationConfiguration().configure()
                    .buildSessionFactory();

        } catch (Throwable ex) {

            // Log exception!
            throw new ExceptionInInitializerError(ex);

        }

    }

    private HibernateUtil() {

    }

    public static Session getSession() throws HibernateException {

        return sessionFactory.openSession();

    }

    public static Session getSession(Interceptor interceptor)
            throws HibernateException {

        return sessionFactory.openSession(interceptor);

    }

}
