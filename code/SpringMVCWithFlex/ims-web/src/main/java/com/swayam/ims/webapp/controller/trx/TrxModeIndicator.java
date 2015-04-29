/*
 * TrxModeIndicator.java
 *
 * Created on Jan 9, 2010 8:24:35 PM
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

package com.swayam.ims.webapp.controller.trx;

/**
 * Utility class to determine whether the transaction is a Purchase Order or a Sales Order.
 * 
 * @author paawak
 */
public class TrxModeIndicator {

    private boolean purchaseMode;

    public boolean isPurchaseMode() {
        return purchaseMode;
    }

    void setPurchaseMode() {
        purchaseMode = true;
    }

    void setSalesMode() {
        purchaseMode = false;
    }

}
