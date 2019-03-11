package com.xumpy.government.domain;

import java.math.BigDecimal;

public interface VatCompensation {
    public Integer getPkId();
    public String getName();
    public BigDecimal getUnitPrice();
}
