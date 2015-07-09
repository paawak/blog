/*
 * CustomTest.java
 *
 * Created on Oct 31, 2010 3:37:24 PM
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

package com.swayam.demo.oracle.hibernate;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * 
 * @author paawak
 */
@Entity
@Table(name = "CUSTOM_DEMO")
public class CustomDemo {

    @Id
    @SequenceGenerator(name = "seq", allocationSize = 1, initialValue = 1, sequenceName = "CUSTOM_DEMO_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private long id;

    @Column
    private String name;

    @Column(name = "TIME_WITH_ZONE")
    @Type(type = "com.swayam.demo.oracle.hibernate.TimeWithZone")
    private Calendar timeWithZone;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getTimeWithZone() {
        return timeWithZone;
    }

    public void setTimeWithZone(Calendar timeWithZone) {
        this.timeWithZone = timeWithZone;
    }

}
