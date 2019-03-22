package com.xumpy.timesheets.controller.model;

import java.math.BigDecimal;
import java.util.List;

public class VatCompOverviewResponseCtrlPojo {
    List<List<Object>> values;
    BigDecimal total;
    BigDecimal vatCompensation;

    public List<List<Object>> getValues() {
        return values;
    }

    public void setValues(List<List<Object>> values) {
        this.values = values;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getVatCompensation() {
        return vatCompensation;
    }

    public void setVatCompensation(BigDecimal vatCompensation) {
        this.vatCompensation = vatCompensation;
    }
}
