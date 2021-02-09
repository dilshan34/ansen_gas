package com.example.ansengas;

public class getsetbillitemtemp {
    String invoiceId, prductId, fullQty, emptyQty, trustQty, trustReQty, refillQty, returnQty, newQty, refillPrice, newrefillprice, newunitprice, newPrice, totalAmount, product_name;

    public getsetbillitemtemp(String invoiceId, String prductId, String fullQty, String emptyQty, String trustQty, String trustReQty, String refillQty, String returnQty, String newQty, String refillPrice, String newPrice, String totalAmount, String product_name) {
        this.invoiceId = invoiceId;
        this.prductId = prductId;
        this.fullQty = fullQty;
        this.emptyQty = emptyQty;
        this.trustQty = trustQty;
        this.trustReQty = trustReQty;
        this.refillQty = refillQty;
        this.returnQty = returnQty;
        this.newQty = newQty;
        this.refillPrice = refillPrice;
        this.newPrice = newPrice;
        this.totalAmount = totalAmount;
        this.product_name = product_name;
    }

    public getsetbillitemtemp(String invoiceId, String prductId, String fullQty, String emptyQty, String trustQty, String trustReQty, String refillQty, String returnQty, String newQty, String refillPrice, String newrefillprice, String newunitprice, String newPrice, String totalAmount, String product_name) {
        this.invoiceId = invoiceId;
        this.prductId = prductId;
        this.fullQty = fullQty;
        this.emptyQty = emptyQty;
        this.trustQty = trustQty;
        this.trustReQty = trustReQty;
        this.refillQty = refillQty;
        this.returnQty = returnQty;
        this.newQty = newQty;
        this.refillPrice = refillPrice;
        this.newrefillprice = newrefillprice;
        this.newunitprice = newunitprice;
        this.newPrice = newPrice;
        this.totalAmount = totalAmount;
        this.product_name = product_name;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getPrductId() {
        return prductId;
    }

    public void setPrductId(String prductId) {
        this.prductId = prductId;
    }

    public String getFullQty() {
        return fullQty;
    }

    public void setFullQty(String fullQty) {
        this.fullQty = fullQty;
    }

    public String getEmptyQty() {
        return emptyQty;
    }

    public void setEmptyQty(String emptyQty) {
        this.emptyQty = emptyQty;
    }

    public String getTrustQty() {
        return trustQty;
    }

    public void setTrustQty(String trustQty) {
        this.trustQty = trustQty;
    }

    public String getTrustReQty() {
        return trustReQty;
    }

    public void setTrustReQty(String trustReQty) {
        this.trustReQty = trustReQty;
    }

    public String getRefillQty() {
        return refillQty;
    }

    public void setRefillQty(String refillQty) {
        this.refillQty = refillQty;
    }

    public String getReturnQty() {
        return returnQty;
    }

    public void setReturnQty(String returnQty) {
        this.returnQty = returnQty;
    }

    public String getNewQty() {
        return newQty;
    }

    public void setNewQty(String newQty) {
        this.newQty = newQty;
    }

    public String getRefillPrice() {
        return refillPrice;
    }

    public void setRefillPrice(String refillPrice) {
        this.refillPrice = refillPrice;
    }

    public String getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(String newPrice) {
        this.newPrice = newPrice;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getNewrefillprice() {
        return newrefillprice;
    }

    public void setNewrefillprice(String newrefillprice) {
        this.newrefillprice = newrefillprice;
    }

    public String getNewunitprice() {
        return newunitprice;
    }

    public void setNewunitprice(String newunitprice) {
        this.newunitprice = newunitprice;
    }
}
