package com.example.ansengas.models;

public class getsetUnpaidBill {

    private String invoiceid,invoicedate,invoicetotal,invoiceamount;

    public getsetUnpaidBill (String invoiceid, String invoicedate, String invoicetotal, String invoiceamount) {
        this.invoiceid = invoiceid;
        this.invoicedate = invoicedate;
        this.invoicetotal = invoicetotal;
        this.invoiceamount = invoiceamount;
    }

    public String getInvoiceid() {
        return invoiceid;
    }

    public void setInvoiceid(String invoiceid) {
        this.invoiceid = invoiceid;
    }

    public String getInvoicedate() {
        return invoicedate;
    }

    public void setInvoicedate(String invoicedate) {
        this.invoicedate = invoicedate;
    }

    public String getInvoicetotal() {
        return invoicetotal;
    }

    public void setInvoicetotal(String invoicetotal) {
        this.invoicetotal = invoicetotal;
    }

    public String getInvoiceamount() {
        return invoiceamount;
    }

    public void setInvoiceamount(String invoiceamount) {
        this.invoiceamount = invoiceamount;
    }
}
