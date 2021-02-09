package com.example.ansengas.models;

public class getSetNewItem {

    String productId, newsaleprice, refillsaleprice, product_name,qty;

    public getSetNewItem(String productId, String newsaleprice, String refillsaleprice, String product_name, String qty) {
        this.productId = productId;
        this.newsaleprice = newsaleprice;
        this.refillsaleprice = refillsaleprice;
        this.product_name = product_name;
        this.qty = qty;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getNewsaleprice() {
        return newsaleprice;
    }

    public void setNewsaleprice(String newsaleprice) {
        this.newsaleprice = newsaleprice;
    }

    public String getRefillsaleprice() {
        return refillsaleprice;
    }

    public void setRefillsaleprice(String refillsaleprice) {
        this.refillsaleprice = refillsaleprice;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
