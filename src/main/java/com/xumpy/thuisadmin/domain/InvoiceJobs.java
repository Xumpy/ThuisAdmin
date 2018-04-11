package com.xumpy.thuisadmin.domain;

import com.xumpy.timesheets.domain.Jobs;

import java.math.BigDecimal;

public interface InvoiceJobs {
    public Integer getPkId();
    public Jobs getJob();
    public Invoices getInvoice();
    public BigDecimal getAmount();
    public String getDescription();
    public Boolean isTimeUnitDays();
}
