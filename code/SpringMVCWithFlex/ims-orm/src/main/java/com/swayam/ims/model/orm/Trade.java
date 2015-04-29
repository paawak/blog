/*
 * Trade.java
 *
 * Created on Aug 8, 2009 4:38:56 PM
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 
 * @author paawak
 */
@Entity
@Table
@NamedQueries( { @NamedQuery(name = Trade.NAMED_QUERY_FIND_MAX_ID, query = "SELECT MAX(trade.id) FROM Trade trade") })
public class Trade extends BaseObject {

    private static final long serialVersionUID = 3109538632124621288L;

    public static final String NAMED_QUERY_FIND_MAX_ID = "findMaxTradeId";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Date tradeDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private TransactionCategory category;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private Party customer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private Currency currency;

    @Column(nullable = false, name = "total_amount")
    private float totalAmount;

    @Column(nullable = false, name = "net_amount")
    private float netAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    public TransactionCategory getCategory() {
        return category;
    }

    public void setCategory(TransactionCategory category) {
        this.category = category;
    }

    public Party getCustomer() {
        return customer;
    }

    public void setCustomer(Party customer) {
        this.customer = customer;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public float getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(float netAmount) {
        this.netAmount = netAmount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (getClass() != obj.getClass())
            return false;
        Trade other = (Trade) obj;
        if (category == null) {
            if (other.category != null)
                return false;
        } else if (!category.equals(other.category))
            return false;
        if (currency == null) {
            if (other.currency != null)
                return false;
        } else if (!currency.equals(other.currency))
            return false;
        if (customer == null) {
            if (other.customer != null)
                return false;
        } else if (!customer.equals(other.customer))
            return false;
        if (Float.floatToIntBits(netAmount) != Float
                .floatToIntBits(other.netAmount))
            return false;
        if (Float.floatToIntBits(totalAmount) != Float
                .floatToIntBits(other.totalAmount))
            return false;
        if (tradeDate == null) {
            if (other.tradeDate != null)
                return false;
        } else if (!tradeDate.equals(other.tradeDate))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 59;
        int result = System.identityHashCode(this);
        result = prime * result
                + ((category == null) ? 0 : category.hashCode());
        result = prime * result
                + ((currency == null) ? 0 : currency.hashCode());
        result = prime * result
                + ((customer == null) ? 0 : customer.hashCode());
        result = prime * result + Float.floatToIntBits(netAmount);
        result = prime * result + Float.floatToIntBits(totalAmount);
        result = prime * result
                + ((tradeDate == null) ? 0 : tradeDate.hashCode());
        return result;
    }
}
