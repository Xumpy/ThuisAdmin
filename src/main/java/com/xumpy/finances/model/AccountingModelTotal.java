package com.xumpy.finances.model;

import java.math.BigDecimal;

/**
 * Created by nico on 19/09/2018.
 */
public class AccountingModelTotal {
    private BigDecimal totalAmountNow;
    private BigDecimal totalUnpaidInvoices;
    private BigDecimal totalGovernmentCosts;

    public BigDecimal getTotalAmountNow() {
        return totalAmountNow;
    }

    public void setTotalAmountNow(BigDecimal totalAmountNow) {
        this.totalAmountNow = totalAmountNow;
    }

    public BigDecimal getTotalUnpaidInvoices() {
        return totalUnpaidInvoices;
    }

    public void setTotalUnpaidInvoices(BigDecimal totalUnpaidInvoices) {
        this.totalUnpaidInvoices = totalUnpaidInvoices;
    }

    public BigDecimal getTotalGovernmentCosts() {
        return totalGovernmentCosts;
    }

    public void setTotalGovernmentCosts(BigDecimal totalGovernmentCosts) {
        this.totalGovernmentCosts = totalGovernmentCosts;
    }
}
