package com.example.ansengas;

public class getSetInvoice {

    String invoId,shop_name,netPrice,payAmount,checqueAmount,balance,rejectReason,date;

    public getSetInvoice(String invoId, String shop_name, String netPrice, String payAmount, String checqueAmount, String balance, String date) {
        this.invoId = invoId;
        this.shop_name = shop_name;
        this.netPrice = netPrice;
        this.payAmount = payAmount;
        this.checqueAmount = checqueAmount;
        this.balance = balance;
        this.date = date;
    }
    public getSetInvoice(String invoId, String shop_name, String netPrice,String rejectReason, String date) {
        this.invoId = invoId;
        this.shop_name = shop_name;
        this.netPrice = netPrice;
        this.date = date;
        this.rejectReason = rejectReason;
    }

    public String getInvoId() {
        return invoId;
    }

    public void setInvoId(String invoId) {
        this.invoId = invoId;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(String netPrice) {
        this.netPrice = netPrice;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public String getChecqueAmount() {
        return checqueAmount;
    }

    public void setChecqueAmount(String checqueAmount) {
        this.checqueAmount = checqueAmount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }
}
