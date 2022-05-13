package com.xumpy.documenprovider.model;

import java.math.BigDecimal;
import java.util.Date;

public class DPDocument {
    private String guid;
    private String financialTransactionEntryGuid;
    private String description;
    private Date date;
    private BigDecimal amount;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getFinancialTransactionEntryGuid() {
        return financialTransactionEntryGuid;
    }

    public void setFinancialTransactionEntryGuid(String financialTransactionEntryGuid) {
        this.financialTransactionEntryGuid = financialTransactionEntryGuid;
    }
}
