package com.xumpy.thuisadmin.domain;

import java.math.BigDecimal;
import java.util.Date;

public interface BedragAccounting {
    public Integer getPkId();
    public Bedragen getBedrag();
    public Date getDatum();
    public BigDecimal getAccountBedrag();
    public String getAccountCode();
    public String getOmschrijving();
    public String getCustomerName();
    public String getVatNumber();
    public String getTaxDescription();
}
