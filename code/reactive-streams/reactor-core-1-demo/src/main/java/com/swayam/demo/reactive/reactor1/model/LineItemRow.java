package com.swayam.demo.reactive.reactor1.model;

import java.time.LocalDate;

public interface LineItemRow {

    int getOrderKey();

    int getPartKey();

    int getSupplierKey();

    int getLineNumber();

    int getQuantity();

    float getExtendedPrice();

    float getDiscount();

    float getTax();

    String getReturnFlag();

    String getLineStatus();

    LocalDate getShippingDate();

    LocalDate getCommitDate();

    LocalDate getReceiptDate();

    String getShippingInstructions();

    String getShipMode();

    String getComment();

}