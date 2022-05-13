package com.xumpy.thuisadmin.services.model;

import com.xumpy.thuisadmin.domain.BedragAccounting;

import java.math.BigDecimal;
import java.util.Date;

public class BedragAccountingSrvPojo implements BedragAccounting {
    private Integer pkId;
    private BedragenSrvPojo bedrag;
    private Date datum;
    private BigDecimal accountBedrag;
    private String accountCode;
    private String omschrijving;
    private String customerName;
    private String vatNumber;
    private String taxDescription;

    @Override
    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    @Override
    public BedragenSrvPojo getBedrag() {
        return bedrag;
    }

    public void setBedrag(BedragenSrvPojo bedrag) {
        this.bedrag = bedrag;
    }

    @Override
    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    @Override
    public BigDecimal getAccountBedrag() {
        return accountBedrag;
    }

    public void setAccountBedrag(BigDecimal accountBedrag) {
        this.accountBedrag = accountBedrag;
    }

    @Override
    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    @Override
    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    @Override
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    @Override
    public String getTaxDescription() {
        return taxDescription;
    }

    public void setTaxDescription(String taxDescription) {
        this.taxDescription = taxDescription;
    }

    public BedragAccountingSrvPojo(){}

    public BedragAccountingSrvPojo(BedragAccounting bedragAccounting){
        this.pkId = bedragAccounting.getPkId();
        this.bedrag = new BedragenSrvPojo(bedragAccounting.getBedrag());
        this.datum = bedragAccounting.getDatum();
        this.accountBedrag = bedragAccounting.getAccountBedrag();
        this.accountCode = bedragAccounting.getAccountCode();
        this.omschrijving = bedragAccounting.getOmschrijving();
        this.customerName = bedragAccounting.getCustomerName();
        this.vatNumber = bedragAccounting.getVatNumber();
        this.taxDescription = bedragAccounting.getTaxDescription();
    }

    @Override
    public String toString() {
        return "BedragAccountingSrvPojo{" +
                "pkId=" + pkId +
                ", bedrag=" + bedrag +
                ", datum=" + datum +
                ", accountBedrag=" + accountBedrag +
                ", accountCode='" + accountCode + '\'' +
                ", omschrijving='" + omschrijving + '\'' +
                ", customerName='" + customerName + '\'' +
                ", vatNumber='" + vatNumber + '\'' +
                ", taxDescription='" + taxDescription + '\'' +
                '}';
    }
}
