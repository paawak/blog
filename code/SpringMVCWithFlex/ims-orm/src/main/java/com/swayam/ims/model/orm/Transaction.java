/*
 * Transaction.java
 *
 * Created on Jul 31, 2009 11:50:08 PM
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

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 
 * @author paawak
 */
@Entity
@Table
public class Transaction extends BaseObject {

    private static final long serialVersionUID = -9116362232532341855L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private Trade trade;

    @Column(nullable = false, name = "trx_date")
    private Date transactionDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "account_group_id")
    private AccountGroup accountGroup;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "trx_category")
    private TransactionCategory transactionCategory;

    @Column(nullable = false)
    private float amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Trade getTrade() {
        return trade;
    }

    public void setTrade(Trade trade) {
        this.trade = trade;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public AccountGroup getAccountGroup() {
        return accountGroup;
    }

    public void setAccountGroup(AccountGroup accountGroup) {
        this.accountGroup = accountGroup;
    }

    public TransactionCategory getTransactionCategory() {
        return transactionCategory;
    }

    public void setTransactionCategory(TransactionCategory transactionCategory) {
        this.transactionCategory = transactionCategory;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((accountGroup == null) ? 0 : accountGroup.hashCode());
        result = prime * result + Float.floatToIntBits(amount);
        result = prime * result + ((trade == null) ? 0 : trade.hashCode());
        result = prime
                * result
                + ((transactionCategory == null) ? 0 : transactionCategory
                        .hashCode());
        result = prime * result
                + ((transactionDate == null) ? 0 : transactionDate.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Transaction other = (Transaction) obj;
        if (accountGroup == null) {
            if (other.accountGroup != null)
                return false;
        } else if (!accountGroup.equals(other.accountGroup))
            return false;
        if (Float.floatToIntBits(amount) != Float.floatToIntBits(other.amount))
            return false;
        if (trade == null) {
            if (other.trade != null)
                return false;
        } else if (!trade.equals(other.trade))
            return false;
        if (transactionCategory == null) {
            if (other.transactionCategory != null)
                return false;
        } else if (!transactionCategory.equals(other.transactionCategory))
            return false;
        if (transactionDate == null) {
            if (other.transactionDate != null)
                return false;
        } else if (!transactionDate.equals(other.transactionDate))
            return false;
        return true;
    }

}
