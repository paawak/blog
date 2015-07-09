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

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;

import com.swayam.demo.oracle.hibernate.HibernateUtil;
import com.swayam.demo.oracle.hibernate.Semester;
import com.swayam.demo.oracle.hibernate.Subject;

/**
 * 
 * @author paawak
 */
public class SemesterInsertTest {

    private Session session;

    @Before
    public void init() {
        session = HibernateUtil.getSession();
    }

    @Test
    public void insert() {

        Set<Subject> subjects = new HashSet<Subject>();

        Semester semester = new Semester();

        for (int i = 1; i < 5; i++) {

            Subject subject = new Subject();
            subject.setSubjectName("subject_" + i);
            subject.setSemester(semester);

            subjects.add(subject);

        }

        semester.setSemesterName("1st sem");
        semester.setSubjects(subjects);

        Transaction tr = session.beginTransaction();
        session.save(semester);
        tr.commit();

    }

}
