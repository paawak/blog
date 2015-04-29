/*
 * PurchaseOrderService.java
 *
 * Created on Jan 2, 2010 10:21:03 PM
 *
 * Copyright (c) 2002 - 2010 : Swayam Inc.
 *
 * P R O P R I E T A R Y & C O N F I D E N T I A L
 *
 * The copyright of this document is vested in Swayam Inc. without
 * whose prior written permission its contents must not be published,
 * adapted or reproduced in any form or disclosed or
 * issued to any third party.
 */

package com.swayam.ims.core.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import com.swayam.ims.core.dao.GenericDao;
import com.swayam.ims.model.orm.Currency;
import com.swayam.ims.model.orm.Item;
import com.swayam.ims.model.orm.Lot;
import com.swayam.ims.model.orm.Party;
import com.swayam.ims.model.orm.Trade;
import com.swayam.ims.model.orm.TradeDetails;
import com.swayam.ims.model.orm.TransactionCategory;

/**
 * 
 * @author paawak
 */
public class PurchaseOrderService {

    private GenericDao<Trade, Long> tradeDao;

    private GenericDao<TradeDetails, Long> tradeDetailsDao;

    private GenericDao<TransactionCategory, Long> trxCatDao;

    private GenericDao<Currency, Long> currencyDao;

    private GenericDao<Party, Long> partyDao;

    private GenericDao<Lot, Long> lotDao;

    private GenericDao<Item, Long> itemDao;

    // @SuppressWarnings("unchecked")
    // public long getNewPurchaseOrderId() {
    //
    // long id = 1;
    //
    // List results = tradeDao.findByNamedQuery(Trade.NAMED_QUERY_FIND_MAX_ID,
    // Collections.EMPTY_MAP);
    //
    // if (results != null && !results.isEmpty()) {
    //
    // Long maxId = (Long) results.get(0);
    //
    // if (maxId != null) {
    //
    // id = maxId + 1;
    //
    // }
    //
    // }
    //
    // return id;
    //
    // }

    // public Lot getLot(long itemId) {
    //
    // Map<String, Object> lotParams = new HashMap<String, Object>(1);
    // lotParams.put(Lot.PARAM_ITEM_ID, itemId);
    // // get the latest lot and the price
    // List<Lot> lotList = lotDao.findByNamedQuery(
    // Lot.FIND_LATEST_LOT_FOR_ITEM, lotParams);
    //
    // if (lotList == null || lotList.isEmpty()) {
    //
    // // for human readable message
    // Item item = itemDao.get(itemId);
    //
    // throw new IllegalArgumentException("No lot found for item `"
    // + item.getName() + "`, code : `" + item.getCode()
    // + "`. Please make sure there is enough stock!");
    // }
    //
    // return lotList.get(0);
    //
    // }

    public boolean savePurchaseOrder(long partyId, float totalPrice,
            float discount, Map<Long, TradeDetailsLean> itemQtyMap) {

        // FIXME: do not hard-code
        TransactionCategory trxCat = trxCatDao.get(1l);

        // FIXME: do not hard-code
        Currency curr = currencyDao.get(1l);

        Party party = partyDao.get(partyId);

        Trade trade = new Trade();
        trade.setCategory(trxCat);
        trade.setCurrency(curr);
        trade.setCustomer(party);
        trade.setTotalAmount(totalPrice);
        trade.setNetAmount(totalPrice - discount);
        trade.setTradeDate(new Date());

        trade = tradeDao.save(trade);

        Iterator<Long> itemIdItr = itemQtyMap.keySet().iterator();

        while (itemIdItr.hasNext()) {

            Long id = itemIdItr.next();

            Item item = itemDao.get(id);
            TradeDetailsLean tdLean = itemQtyMap.get(id);
            int qty = tdLean.getQuantity();

            // update stockInHands for item table
            Integer stockInHand = item.getStockInHand();

            if (stockInHand == null) {
                stockInHand = 0;
            }

            stockInHand += qty;
            item.setStockInHand(stockInHand);
            item = itemDao.save(item);

            // update trade details table
            TradeDetails tradeDetails = new TradeDetails();
            tradeDetails.setItem(item);
            tradeDetails.setQuantity(qty);
            tradeDetails.setTotalPrice(tdLean.getTotalPrice());
            tradeDetails.setTradeHeader(trade);
            tradeDetailsDao.save(tradeDetails);

            // create a new lot
            Lot itemLot = new Lot();
            itemLot.setProcuredOn(new Date());
            String batchNo = tdLean.getBatchNo();

            if (batchNo != null && "".equals(batchNo.trim())) {
                batchNo = null;
            }

            itemLot.setBatchNo(batchNo);
            itemLot.setCostPrice(tdLean.getRate());
            itemLot.setExpiryDate(tdLean.getExpiryDate());
            itemLot.setManufacturedDate(tdLean.getManufactureDate());
            itemLot.setOpeningStock(qty);
            lotDao.save(itemLot);

        }

        return true;

    }

    public void setTradeDao(GenericDao<Trade, Long> tradeDao) {
        this.tradeDao = tradeDao;
    }

    public void setTradeDetailsDao(
            GenericDao<TradeDetails, Long> tradeDetailsDao) {
        this.tradeDetailsDao = tradeDetailsDao;
    }

    public void setTrxCatDao(GenericDao<TransactionCategory, Long> trxCatDao) {
        this.trxCatDao = trxCatDao;
    }

    public void setCurrencyDao(GenericDao<Currency, Long> currencyDao) {
        this.currencyDao = currencyDao;
    }

    public void setPartyDao(GenericDao<Party, Long> partyDao) {
        this.partyDao = partyDao;
    }

    public void setLotDao(GenericDao<Lot, Long> lotDao) {
        this.lotDao = lotDao;
    }

    public void setItemDao(GenericDao<Item, Long> itemDao) {
        this.itemDao = itemDao;
    }

    public static class TradeDetailsLean {

        private int quantity;
        private float rate;
        private float totalPrice;
        private String batchNo;
        private Date manufactureDate;
        private Date expiryDate;

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

        public float getRate() {
            return rate;
        }

        public void setRate(float rate) {
            this.rate = rate;
        }

        public String getBatchNo() {
            return batchNo;
        }

        public void setBatchNo(String batchNo) {
            this.batchNo = batchNo;
        }

        public Date getManufactureDate() {
            return manufactureDate;
        }

        public void setManufactureDate(Date manufactureDate) {
            this.manufactureDate = manufactureDate;
        }

        public Date getExpiryDate() {
            return expiryDate;
        }

        public void setExpiryDate(Date expiryDate) {
            this.expiryDate = expiryDate;
        }

    }

}
