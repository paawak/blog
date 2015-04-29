/*
 * AccountGroup.java
 *
 * Created on Jun 9, 2009 2:43:59 PM
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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 
 * @author paawak
 */
@Entity
@Table(name = "account_group")
@NamedQueries( { @NamedQuery(name = AccountGroup.NAMED_QUERY_FIND_MAX_ID, query = "SELECT MAX(acg.id) FROM AccountGroup acg") })
public class AccountGroup extends BaseObject {

    private static final long serialVersionUID = 8651011772980546778L;

    public static final String NAMED_QUERY_FIND_MAX_ID = "findMaxAccountGroupId";

    @Id
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String name;

    @Column(length = 50)
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent")
    private AccountGroup parent;

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

    public AccountGroup getParent() {
        return parent;
    }

    public void setParent(AccountGroup parent) {
        this.parent = parent;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = System.identityHashCode(this);
        result = prime * result
                + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((parent == null) ? 0 : parent.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (getClass() != obj.getClass())
            return false;
        AccountGroup other = (AccountGroup) obj;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (parent == null) {
            if (other.parent != null)
                return false;
        } else if (!parent.equals(other.parent))
            return false;
        return true;
    }

}
