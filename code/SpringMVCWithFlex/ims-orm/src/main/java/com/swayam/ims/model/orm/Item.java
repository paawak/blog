/*
 * Item.java
 *
 * Created on Jun 1, 2009 12:50:10 AM
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
public class Item extends BaseObject {

    private static final long serialVersionUID = 5729868965288261711L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 20, unique = true)
    private String code;

    @Column(length = 15, unique = true)
    private String name;

    @Column(length = 50)
    private String description;

    @Column
    private Float sellingPrice;

    @Column
    private Integer stockInHand;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private ItemGroup itemGroup;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Currency currency;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Unit unit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public ItemGroup getItemGroup() {
        return itemGroup;
    }

    public void setItemGroup(ItemGroup itemGroup) {
        this.itemGroup = itemGroup;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Float getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Float sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Integer getStockInHand() {
        return stockInHand;
    }

    public void setStockInHand(Integer stockInHand) {
        this.stockInHand = stockInHand;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = System.identityHashCode(this);
        result = prime * result
                + ((itemGroup == null) ? 0 : itemGroup.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (getClass() != obj.getClass())
            return false;
        Item other = (Item) obj;
        if (itemGroup == null) {
            if (other.itemGroup != null)
                return false;
        } else if (!itemGroup.equals(other.itemGroup))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

}
