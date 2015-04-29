/*
 * ItemGroup.java
 *
 * Created on May 30, 2009 2:56:09 PM
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

package com.swayam.ims.model.orm;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * 
 * @author paawak
 */
@Entity
public class ItemGroup implements Serializable {

    private static final long serialVersionUID = 6183525384802106499L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String name;

    @Column(length = 100)
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parentgroup")
    private ItemGroup parentGroup;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "createdby")
    private User createdBy;

    private Date createdOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ItemGroup getParentGroup() {
        return parentGroup;
    }

    public void setParentGroup(ItemGroup parentGroup) {
        this.parentGroup = parentGroup;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

}
