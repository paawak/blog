/*
 * Lot.java
 *
 * Created on Jun 1, 2009 3:58:30 PM
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
// @NamedQueries( { @NamedQuery(name = Lot.FIND_LATEST_LOT_FOR_ITEM, query = "SELECT lot FROM Lot lot WHERE lot.item.id = :"
// + Lot.PARAM_ITEM_ID
// + " AND lot.procuredOn = "
// + "(SELECT MAX(lot.procuredOn) FROM Lot lot WHERE lot.item.id = :"
// + Lot.PARAM_ITEM_ID + ")") })
public class Lot implements Serializable {

    private static final long serialVersionUID = 6514962799603561015L;

    // public static final String FIND_LATEST_LOT_FOR_ITEM = "findLatestLotForItem";
    // public static final String PARAM_ITEM_ID = "itemId";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Item item;

    @Column(length = 20, unique = true, nullable = true)
    private String batchNo;

    @Column(nullable = false)
    private Date procuredOn;

    @Column(nullable = false)
    private Float costPrice;

    @Column
    private Date manufacturedDate;

    @Column
    private Date expiryDate;

    @Column
    private Integer openingStock;

    @Column
    private Integer stockInHand;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private User enteredBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Date getProcuredOn() {
        return procuredOn;
    }

    public void setProcuredOn(Date procuredOn) {
        this.procuredOn = procuredOn;
    }

    public Float getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Float costPrice) {
        this.costPrice = costPrice;
    }

    public Date getManufacturedDate() {
        return manufacturedDate;
    }

    public void setManufacturedDate(Date manufacturedDate) {
        this.manufacturedDate = manufacturedDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public User getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(User enteredBy) {
        this.enteredBy = enteredBy;
    }

    public Integer getOpeningStock() {
        return openingStock;
    }

    public void setOpeningStock(Integer openingStock) {
        this.openingStock = openingStock;
    }

    public Integer getStockInHand() {
        return stockInHand;
    }

    public void setStockInHand(Integer stockInHand) {
        this.stockInHand = stockInHand;
    }

}
