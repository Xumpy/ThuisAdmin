package com.xumpy.finances.model.excel;

import java.util.Date;
import java.util.List;

public class Costs {
    private Integer bedragId;
    private Date date;
    private String description;
    private double expectedWeight;
    private double amount;
    private List<Document> documents;

    public Integer getBedragId() {
        return bedragId;
    }

    public void setBedragId(Integer bedragId) {
        this.bedragId = bedragId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getExpectedWeight() {
        return expectedWeight;
    }

    public void setExpectedWeight(double expectedWeight) {
        this.expectedWeight = expectedWeight;
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

    public Costs(){}
    public Costs(Integer bedragId, Date date, String description, double expectedWeight, double amount, List<Document> documents){
        this.bedragId = bedragId;
        this.date = date;
        this.description = description;
        this.expectedWeight = expectedWeight;
        this.amount = amount;
        this.documents = documents;
    }
}
