package com.example.ansengas.models;

public class getSetCustomer {

    String cusid, shop_name, mobile, outStanding,address,creditlimit;

    public getSetCustomer(String cusid, String shop_name, String mobile, String outStanding, String address, String creditlimit) {
        this.cusid = cusid;
        this.shop_name = shop_name;
        this.mobile = mobile;
        this.outStanding = outStanding;
        this.address = address;
        this.creditlimit = creditlimit;
    }

    public String getCusid() {
        return cusid;
    }

    public void setCusid(String cusid) {
        this.cusid = cusid;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOutStanding() {
        return outStanding;
    }

    public void setOutStanding(String outStanding) {
        this.outStanding = outStanding;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreditlimit() {
        return creditlimit;
    }

    public void setCreditlimit(String creditlimit) {
        this.creditlimit = creditlimit;
    }
}
