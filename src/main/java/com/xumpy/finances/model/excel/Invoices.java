package com.xumpy.finances.model.excel;

import java.util.Date;
import java.util.List;

public class Invoices {
    private Date date;
    private String invoiceId;
    private double amount;
    private List<Document> documents;

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public Invoices(){}
    public Invoices(Date date, String invoiceId, double amount,  List<Document> documents){
        this.date = date;
        this.invoiceId = invoiceId;
        this.amount = amount;
        this.documents = documents;
    }

}
