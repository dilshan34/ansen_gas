package com.example.ansengas;

import java.util.ArrayList;

public class getsetInvoiceMain {

    String ID,customerId ,netPrice ,payAmount ,checqueAmount ,balance ,date,rejectReason;
    ArrayList<getsetbillitemtemp> arrayList;

    public getsetInvoiceMain(String ID, String customerId, String netPrice, String payAmount, String checqueAmount, String balance, String date, ArrayList<getsetbillitemtemp> arrayList) {
        this.ID = ID;
        this.customerId = customerId;
        this.netPrice = netPrice;
        this.payAmount = payAmount;
        this.checqueAmount = checqueAmount;
        this.balance = balance;
        this.date = date;
        this.arrayList = arrayList;
    }
    public getsetInvoiceMain(String ID, String customerId, String netPrice, String date,String rejectReason, ArrayList<getsetbillitemtemp> arrayList) {
        this.ID = ID;
        this.customerId = customerId;
        this.netPrice = netPrice;
        this.date = date;
        this.rejectReason = rejectReason;
        this.arrayList = arrayList;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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

    public ArrayList<getsetbillitemtemp> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<getsetbillitemtemp> arrayList) {
        this.arrayList = arrayList;
    }
}
