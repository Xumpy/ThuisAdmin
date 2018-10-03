package com.xumpy.government.domain;

import java.math.BigDecimal;

public interface GovernmentCost {
    public Integer getPkId();
    public BigDecimal getStartAmount();
    public BigDecimal getTaxPercentage();
    public GovernmentCostType getGovernmentCostType();
    public BusinessForm getBusinessForm();
}
