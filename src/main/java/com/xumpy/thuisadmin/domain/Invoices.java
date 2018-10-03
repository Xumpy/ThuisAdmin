package com.xumpy.thuisadmin.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by nico on 27/02/2018.
 */
public interface Invoices {
    public Integer getPkId();
    public Rekeningen getRekening();
    public String getInvoiceId();
    public Date getInvoiceDate();
    public Date getInvoiceDueDate();
    public String getInvoiceRef();
    public String getVatNumber();
    public BigDecimal getVatAmount();
    public String getDescription();
    public Boolean getClosed();
}
