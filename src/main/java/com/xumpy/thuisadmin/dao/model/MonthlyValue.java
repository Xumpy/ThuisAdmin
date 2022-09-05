package com.xumpy.thuisadmin.dao.model;

import java.math.BigDecimal;

public interface MonthlyValue {
    public Integer getMonth();
    public Integer getCodeId();
    public String getDescription();
    public String getDatum();
    public BigDecimal getBedrag();
    public String getMainGroup();
    public Integer getBedragId();
}
