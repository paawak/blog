/*
 * TradeDetails.java
 *
 * Created on Aug 8, 2009 5:34:04 PM
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
@Entity(name = "trade_details")
public class TradeDetails implements Serializable {

    private static final long serialVersionUID = 1694240781234093096L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "trade_header")
    private Trade tradeHeader;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Item item;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private float totalPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Trade getTradeHeader() {
        return tradeHeader;
    }

    public void setTradeHeader(Trade tradeHeader) {
        this.tradeHeader = tradeHeader;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

}
