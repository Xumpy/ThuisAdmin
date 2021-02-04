package com.xumpy.timesheets.services.model;

import java.math.BigDecimal;
import java.util.List;

public class AbsenceCalenderSrvPojo {
    private BigDecimal totalPossibleWorkDays;
    private BigDecimal totalAbsenceDays;
    private List<AbsenceCalenderInfoSrvPojo> calenderInfo;

    public BigDecimal getTotalPossibleWorkDays() {
        return totalPossibleWorkDays;
    }

    public void setTotalPossibleWorkDays(BigDecimal totalPossibleWorkDays) {
        this.totalPossibleWorkDays = totalPossibleWorkDays;
    }

    public BigDecimal getTotalAbsenceDays() {
        return totalAbsenceDays;
    }

    public void setTotalAbsenceDays(BigDecimal totalAbsenceDays) {
        this.totalAbsenceDays = totalAbsenceDays;
    }

    public List<AbsenceCalenderInfoSrvPojo> getCalenderInfo() {
        return calenderInfo;
    }

    public void setCalenderInfo(List<AbsenceCalenderInfoSrvPojo> calenderInfo) {
        this.calenderInfo = calenderInfo;
    }
}
