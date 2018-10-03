package com.xumpy.finances.model;

import java.math.BigDecimal;

/**
 * Created by nico on 19/09/2018.
 */
public class BusinessCost {
    private BigDecimal amount;
    private BigDecimal tax;
    private BigDecimal weight;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }
}
