package com.xumpy.government.domain;

import java.math.BigDecimal;

public interface FinancialYearGovernmentCostTypes {
    public Integer getPkId();
    public FinancialYears getFinancialYear();
    public GovernmentCostType getGovernmentCostType();
    public BigDecimal getActualCost();
}
