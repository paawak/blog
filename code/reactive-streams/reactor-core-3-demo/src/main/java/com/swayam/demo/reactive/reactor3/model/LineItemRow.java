package com.swayam.demo.reactive.reactor3.model;

import java.time.LocalDate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.swayam.demo.reactive.reactor3.xml.LocalDateAdapter;

@XmlRootElement(name = "T")
@XmlAccessorType(XmlAccessType.FIELD)
public class LineItemRow {

    @XmlElement(name = "L_ORDERKEY")
    private int orderKey;

    @XmlElement(name = "L_PARTKEY")
    private int partKey;

    @XmlElement(name = "L_SUPPKEY")
    private int supplierKey;

    @XmlElement(name = "L_LINENUMBER")
    private int lineNumber;

    @XmlElement(name = "L_QUANTITY")
    private int quantity;

    @XmlElement(name = "L_EXTENDEDPRICE")
    private float extendedPrice;

    @XmlElement(name = "L_DISCOUNT")
    private float discount;

    @XmlElement(name = "L_TAX")
    private float tax;

    @XmlElement(name = "L_RETURNFLAG")
    private String returnFlag;

    @XmlElement(name = "L_LINESTATUS")
    private String lineStatus;

    @XmlElement(name = "L_SHIPDATE")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate shippingDate;

    @XmlElement(name = "L_COMMITDATE")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate commitDate;

    @XmlElement(name = "L_RECEIPTDATE")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate receiptDate;

    @XmlElement(name = "L_SHIPINSTRUCT")
    private String shippingInstructions;

    @XmlElement(name = "L_SHIPMODE")
    private String shipMode;

    @XmlElement(name = "L_COMMENT")
    private String comment;

    public LineItemRow() {

    }

    public int getOrderKey() {
	return orderKey;
    }

    public void setOrderKey(int orderKey) {
	this.orderKey = orderKey;
    }

    public int getPartKey() {
	return partKey;
    }

    public void setPartKey(int partKey) {
	this.partKey = partKey;
    }

    public int getSupplierKey() {
	return supplierKey;
    }

    public void setSupplierKey(int supplierKey) {
	this.supplierKey = supplierKey;
    }

    public int getLineNumber() {
	return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
	this.lineNumber = lineNumber;
    }

    public int getQuantity() {
	return quantity;
    }

    public void setQuantity(int quantity) {
	this.quantity = quantity;
    }

    public float getExtendedPrice() {
	return extendedPrice;
    }

    public void setExtendedPrice(float extendedPrice) {
	this.extendedPrice = extendedPrice;
    }

    public float getDiscount() {
	return discount;
    }

    public void setDiscount(float discount) {
	this.discount = discount;
    }

    public float getTax() {
	return tax;
    }

    public void setTax(float tax) {
	this.tax = tax;
    }

    public String getReturnFlag() {
	return returnFlag;
    }

    public void setReturnFlag(String returnFlag) {
	this.returnFlag = returnFlag;
    }

    public String getLineStatus() {
	return lineStatus;
    }

    public void setLineStatus(String lineStatus) {
	this.lineStatus = lineStatus;
    }

    public LocalDate getShippingDate() {
	return shippingDate;
    }

    public void setShippingDate(LocalDate shippingDate) {
	this.shippingDate = shippingDate;
    }

    public LocalDate getCommitDate() {
	return commitDate;
    }

    public void setCommitDate(LocalDate commitDate) {
	this.commitDate = commitDate;
    }

    public LocalDate getReceiptDate() {
	return receiptDate;
    }

    public void setReceiptDate(LocalDate receiptDate) {
	this.receiptDate = receiptDate;
    }

    public String getShippingInstructions() {
	return shippingInstructions;
    }

    public void setShippingInstructions(String shippingInstructions) {
	this.shippingInstructions = shippingInstructions;
    }

    public String getShipMode() {
	return shipMode;
    }

    public void setShipMode(String shipMode) {
	this.shipMode = shipMode;
    }

    public String getComment() {
	return comment;
    }

    public void setComment(String comment) {
	this.comment = comment;
    }

    @Override
    public String toString() {
	return "LineItemRow [orderKey=" + orderKey + ", partKey=" + partKey + ", supplierKey=" + supplierKey + ", lineNumber=" + lineNumber + ", quantity=" + quantity + ", extendedPrice="
		+ extendedPrice + ", discount=" + discount + ", tax=" + tax + ", returnFlag=" + returnFlag + ", lineStatus=" + lineStatus + ", shippingDate=" + shippingDate + ", commitDate="
		+ commitDate + ", receiptDate=" + receiptDate + ", shippingInstructions=" + shippingInstructions + ", shipMode=" + shipMode + ", comment=" + comment + "]";
    }

}
