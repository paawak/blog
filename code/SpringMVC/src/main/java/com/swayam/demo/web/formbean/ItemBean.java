/*
 * ItemBean.java
 *
 * Created on Aug 28, 2010 8:37:19 PM
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

package com.swayam.demo.web.formbean;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author paawak
 */
public class ItemBean {

    private List<ItemRow> items;

    private float totalPrice;

    private Date expectedDelivery;

    public List<ItemRow> getItems() {
        return items;
    }

    public void setItems(List<ItemRow> items) {
        this.items = items;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getExpectedDelivery() {
        return expectedDelivery;
    }

    public void setExpectedDelivery(Date expectedDelivery) {
        this.expectedDelivery = expectedDelivery;
    }

    public static class ItemRow {

        private boolean selected;

        private String itemName;

        private float price;

        private int quantity;

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result
                    + ((itemName == null) ? 0 : itemName.hashCode());
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
            ItemRow other = (ItemRow) obj;
            if (itemName == null) {
                if (other.itemName != null)
                    return false;
            } else if (!itemName.equals(other.itemName))
                return false;
            return true;
        }

    }

}
