/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.domain.OverviewWork;

import java.math.BigDecimal;

public interface OverviewWorkDetails {
    public BigDecimal getWorkedWeekHours();
    public BigDecimal getWorkedWeekDays();
    public BigDecimal getWorkedWeekendHours();
    public BigDecimal getWorkedWeekendDays();
    public BigDecimal getWeekDays();
    public BigDecimal getWeekendDays();
    public BigDecimal getHoursToWorkPerDay();
    public BigDecimal getHoursPayedPerDay();
    public BigDecimal getOvertimeHours();
    public BigDecimal getOvertimeDays();
}