/*
 * Subject.java
 *
 * Created on Nov 26, 2010 12:28:22 AM
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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/**
 * 
 * @author paawak
 */
@Entity
public class Subject implements Serializable {

    private static final long serialVersionUID = 5705981176568667418L;

    @Id
    @Column(name = "SUBJECT_ID")
    @SequenceGenerator(name = "seq", allocationSize = 1, initialValue = 1, sequenceName = "SEQ_SUBJECT")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private Long subjectId;

    @Column(name = "SUBJECT_NAME")
    private String subjectName;

    @ManyToOne
    @JoinColumn(name = "SEMESTER_ID_FK")
    private Semester semester;

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

}
