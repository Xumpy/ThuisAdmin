package com.xumpy.government.domain;

import java.math.BigDecimal;

public interface FinancialYears {
    public Integer getPkId();
    public Integer getYear();
    public BigDecimal getRevenue();
    public BigDecimal getCosts();
}
