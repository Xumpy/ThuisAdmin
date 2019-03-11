package com.xumpy.timesheets.controller.model;

import java.math.BigDecimal;

/**
 * Created by nico on 01/03/2019.
 */
public class JobVatCompensationRestPojo {
    private Integer pkId;
    private String name;
    private BigDecimal unitPrice;

    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}
