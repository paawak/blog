/*
 * Party.java
 *
 * Created on Jul 31, 2009 11:58:30 PM
 *
 * Copyright (c) 2002 - 2009 : Swayam Inc.
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
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * This stores account details of creditors, debtors, banks and other general accounts.
 * 
 * @author paawak
 */
@Entity
public class Party implements Serializable {

    private static final long serialVersionUID = -8509701917322346595L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 20, unique = true, nullable = false)
    private String name;

    @Column(length = 100)
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private PartyType type;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private AccountGroup accountGroup;

    @Embedded
    private Address address = new Address();

    @Column(nullable = false)
    private Date openedOn;

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

    public PartyType getType() {
        return type;
    }

    public void setType(PartyType type) {
        this.type = type;
    }

    public AccountGroup getAccountGroup() {
        return accountGroup;
    }

    public void setAccountGroup(AccountGroup accountGroup) {
        this.accountGroup = accountGroup;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Date getOpenedOn() {
        return openedOn;
    }

    public void setOpenedOn(Date openedOn) {
        this.openedOn = openedOn;
    }

}
