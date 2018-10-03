package com.xumpy.finances.services;

import com.xumpy.thuisadmin.domain.Bedragen;
import com.xumpy.thuisadmin.domain.Invoices;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BedragWeightPojo {
    private BigDecimal bedragExclusiveTax;
    private BigDecimal bedragTax;
    private BigDecimal bedragWeightExclusiveTax;
    private BigDecimal bedragWeightTax;
    private BigDecimal bedragLeftOverExclusiveTax;
    private BigDecimal bedragLeftOverTax;

    public BigDecimal getBedragExclusiveTax() {
        return bedragExclusiveTax;
    }

    public void setBedragExclusiveTax(BigDecimal bedragExclusiveTax) {
        this.bedragExclusiveTax = bedragExclusiveTax;
    }

    public BigDecimal getBedragWeightExclusiveTax() {
        return bedragWeightExclusiveTax;
    }

    public void setBedragWeightExclusiveTax(BigDecimal bedragWeightExclusiveTax) {
        this.bedragWeightExclusiveTax = bedragWeightExclusiveTax;
    }

    public BigDecimal getBedragWeightTax() {
        return bedragWeightTax;
    }

    public void setBedragWeightTax(BigDecimal bedragWeightTax) {
        this.bedragWeightTax = bedragWeightTax;
    }

    public BigDecimal getBedragLeftOverExclusiveTax() {
        return bedragLeftOverExclusiveTax;
    }

    public void setBedragLeftOverExclusiveTax(BigDecimal bedragLeftOverExclusiveTax) {
        this.bedragLeftOverExclusiveTax = bedragLeftOverExclusiveTax;
    }

    public BigDecimal getBedragLeftOverTax() {
        return bedragLeftOverTax;
    }

    public void setBedragLeftOverTax(BigDecimal bedragLeftOverTax) {
        this.bedragLeftOverTax = bedragLeftOverTax;
    }

    public BigDecimal getBedragTax() {
        return bedragTax;
    }

    public void setBedragTax(BigDecimal bedragTax) {
        this.bedragTax = bedragTax;
    }

    public BedragWeightPojo(){
        this.bedragExclusiveTax = new BigDecimal(0);
        this.bedragTax = new BigDecimal(0);
        this.bedragWeightExclusiveTax = new BigDecimal(0);
        this.bedragWeightTax = new BigDecimal(0);
        this.bedragLeftOverExclusiveTax = new BigDecimal(0);
        this.bedragLeftOverTax = new BigDecimal(0);
    }

    public BedragWeightPojo(Bedragen bedrag) {
        this.bedragExclusiveTax = AmountTaxAndWeightCalculator.amountExclusiefTax(bedrag.getBedrag(), bedrag.getTaxPercentagePaid()).setScale(4, RoundingMode.HALF_UP);
        this.bedragTax = AmountTaxAndWeightCalculator.amountTax(bedrag.getBedrag(), bedrag.getTaxPercentagePaid()).setScale(4, RoundingMode.HALF_UP);
        this.bedragWeightExclusiveTax = AmountTaxAndWeightCalculator.applyWeight(this.bedragExclusiveTax, bedrag.getWeightAccountancy()).setScale(4, RoundingMode.HALF_UP);
        this.bedragWeightTax = AmountTaxAndWeightCalculator.applyWeight(this.bedragTax, bedrag.getWeightAccountancy()).setScale(4, RoundingMode.HALF_UP);
        this.bedragLeftOverExclusiveTax = this.bedragExclusiveTax.subtract(this.bedragWeightExclusiveTax).setScale(4, RoundingMode.HALF_UP);
        this.bedragLeftOverTax = this.bedragTax.subtract(this.bedragWeightTax).setScale(4, RoundingMode.HALF_UP);
    }

    public BedragWeightPojo(Invoices invoice, BigDecimal invoiceAmount) {
        this.bedragExclusiveTax = invoiceAmount;
        this.bedragTax = invoiceAmount.divide(new BigDecimal(100)).multiply(invoice.getVatAmount());
        this.bedragWeightExclusiveTax = this.bedragExclusiveTax;
        this.bedragWeightTax = this.bedragTax;
        this.bedragLeftOverExclusiveTax = new BigDecimal(0);
        this.bedragLeftOverTax = new BigDecimal(0);
    }

    public void add (BedragWeightPojo bedragWeightPojo){
        this.bedragExclusiveTax = this.bedragExclusiveTax.add(bedragWeightPojo.getBedragExclusiveTax());
        this.bedragTax = this.bedragTax.add(bedragWeightPojo.getBedragTax());
        this.bedragWeightExclusiveTax = this.bedragWeightExclusiveTax.add(bedragWeightPojo.getBedragWeightExclusiveTax());
        this.bedragWeightTax = this.bedragWeightTax.add(bedragWeightPojo.getBedragWeightTax());
        this.bedragLeftOverExclusiveTax = this.bedragLeftOverExclusiveTax.add(bedragWeightPojo.getBedragLeftOverExclusiveTax());
        this.bedragLeftOverTax = this.bedragLeftOverTax.add(bedragWeightPojo.getBedragLeftOverTax());
    }
}
