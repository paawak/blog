/*
 * DoubleAccountingDetails.java
 *
 * Created on Aug 10, 2009 11:09:46 PM
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

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "double_accounting_details")
@NamedQueries( { @NamedQuery(name = DoubleAccountingDetails.FIND_DETAILS_BY_TRX_CATEGORY, query = "SELECT details FROM DoubleAccountingDetails details WHERE details.transactionCategory = :transactionCategory ") })
public class DoubleAccountingDetails extends BaseObject {

    private static final long serialVersionUID = -8325231939332972684L;

    public static final String FIND_DETAILS_BY_TRX_CATEGORY = "findDetailsByTransactionCategory";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "trx_category", nullable = false, unique = true)
    private TransactionCategory transactionCategory;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "complementary_account_grp", nullable = false)
    private AccountGroup complementaryAccountGroup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransactionCategory getTransactionCategory() {
        return transactionCategory;
    }

    public void setTransactionCategory(TransactionCategory transactionCategory) {
        this.transactionCategory = transactionCategory;
    }

    public AccountGroup getComplementaryAccountGroup() {
        return complementaryAccountGroup;
    }

    public void setComplementaryAccountGroup(
            AccountGroup complementaryAccountGroup) {
        this.complementaryAccountGroup = complementaryAccountGroup;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = System.identityHashCode(this);
        result = prime
                * result
                + ((complementaryAccountGroup == null) ? 0
                        : complementaryAccountGroup.hashCode());
        result = prime
                * result
                + ((transactionCategory == null) ? 0 : transactionCategory
                        .hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (getClass() != obj.getClass())
            return false;
        DoubleAccountingDetails other = (DoubleAccountingDetails) obj;
        if (complementaryAccountGroup == null) {
            if (other.complementaryAccountGroup != null)
                return false;
        } else if (!complementaryAccountGroup
                .equals(other.complementaryAccountGroup))
            return false;
        if (transactionCategory == null) {
            if (other.transactionCategory != null)
                return false;
        } else if (!transactionCategory.equals(other.transactionCategory))
            return false;
        return true;
    }

}
