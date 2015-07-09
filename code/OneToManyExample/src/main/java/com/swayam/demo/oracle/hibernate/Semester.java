/*
 * Semester.java
 *
 * Created on Nov 26, 2010 12:25:36 AM
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

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

/**
 * 
 * @author paawak
 */
@Entity
public class Semester implements Serializable {

    private static final long serialVersionUID = -6067841723974478563L;

    @Id
    @Column(name = "SEMESTER_ID")
    @SequenceGenerator(name = "seq", allocationSize = 1, initialValue = 1, sequenceName = "SEQ_SEMESTER")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private Long semesterId;

    @Column(name = "SEMESTER_NAME")
    private String semesterName;

    @OneToMany(mappedBy = "semester", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Subject> subjects;

    public Long getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(Long semesterId) {
        this.semesterId = semesterId;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }

}
