package com.xumpy.finances.model;

import java.math.BigDecimal;

public class AmountAndTax implements Comparable<AmountAndTax>{
    BigDecimal amount;
    BigDecimal tax;

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

    public int compareTo(AmountAndTax compareAmountAndTax) {
        return this.amount.compareTo(compareAmountAndTax.getAmount());
    }

    public AmountAndTax(BigDecimal amount, BigDecimal tax){
        this.amount = amount;
        this.tax = tax;
    }

    public String toString(){
        return "{amount = " + amount.toString() + "}, {tax = " + tax.toString() + "}";
    }
}
