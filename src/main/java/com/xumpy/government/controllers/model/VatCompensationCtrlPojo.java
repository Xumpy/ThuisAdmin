package com.xumpy.government.controllers.model;

import com.xumpy.government.domain.VatCompensation;

import java.math.BigDecimal;

/**
 * Created by nico on 02/01/2019.
 */
public class VatCompensationCtrlPojo implements VatCompensation{
    private Integer pkId;
    private String name;
    private BigDecimal unitPrice;

    @Override
    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public VatCompensationCtrlPojo(){}

    public VatCompensationCtrlPojo(VatCompensation vatCompensation){
        this.pkId = vatCompensation.getPkId();
        this.name = vatCompensation.getName();
        this.unitPrice = vatCompensation.getUnitPrice();
    }
}
