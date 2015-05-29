/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services.model.OverviewWorkSrvPojo;

import com.xumpy.timesheets.domain.OverviewWork.OverviewWorkDetails;
import java.math.BigDecimal;

public class OverviewWorkDetailsSrvPojo implements OverviewWorkDetails{

    private BigDecimal workedWeekHours;
    private BigDecimal workedWeekDays;
    private BigDecimal workedWeekendHours;
    private BigDecimal workedWeekendDays;
    private BigDecimal weekDays;
    private BigDecimal weekendDays;
    private BigDecimal hoursToWorkPerDay;
    private BigDecimal hoursPayedPerDay;
    private BigDecimal overtimeHours;
    private BigDecimal overtimeDays;

    public void setHoursPayedPerDay(BigDecimal hoursPayedPerDay) {
        this.hoursPayedPerDay = hoursPayedPerDay;
    }

    public void setWorkedWeekHours(BigDecimal workedWeekHours) {
        this.workedWeekHours = workedWeekHours;
    }

    public void setWorkedWeekDays(BigDecimal workedWeekDays) {
        this.workedWeekDays = workedWeekDays;
    }

    public void setWorkedWeekendHours(BigDecimal workedWeekendHours) {
        this.workedWeekendHours = workedWeekendHours;
    }

    public void setWorkedWeekendDays(BigDecimal workedWeekendDays) {
        this.workedWeekendDays = workedWeekendDays;
    }

    public void setWeekDays(BigDecimal weekDays) {
        this.weekDays = weekDays;
    }

    public void setWeekendDays(BigDecimal weekendDays) {
        this.weekendDays = weekendDays;
    }

    public void setHoursToWorkPerDay(BigDecimal hoursToWorkPerDay) {
        this.hoursToWorkPerDay = hoursToWorkPerDay;
    }

    public void setOvertimeHours(BigDecimal overtimeHours) {
        this.overtimeHours = overtimeHours;
    }

    public void setOvertimeDays(BigDecimal overtimeDays) {
        this.overtimeDays = overtimeDays;
    }

    @Override
    public BigDecimal getWorkedWeekHours() {
        return this.workedWeekHours;
    }

    @Override
    public BigDecimal getWorkedWeekDays() {
        return this.workedWeekDays;
    }

    @Override
    public BigDecimal getWorkedWeekendHours() {
        return this.workedWeekendHours;
    }

    @Override
    public BigDecimal getWorkedWeekendDays() {
        return this.workedWeekendDays;
    }

    @Override
    public BigDecimal getWeekDays() {
        return this.weekDays;
    }

    @Override
    public BigDecimal getWeekendDays() {
        return this.weekendDays;
    }

    @Override
    public BigDecimal getHoursToWorkPerDay() {
        return this.hoursToWorkPerDay;
    }

    @Override
    public BigDecimal getOvertimeHours() {
        return this.overtimeHours;
    }

    @Override
    public BigDecimal getOvertimeDays() {
        return this.overtimeDays;
    }

    public OverviewWorkDetailsSrvPojo(){}

    public OverviewWorkDetailsSrvPojo(OverviewWorkDetails overviewWorkDetails){
        this.hoursToWorkPerDay = overviewWorkDetails.getHoursToWorkPerDay();
        this.overtimeDays = overviewWorkDetails.getOvertimeDays();
        this.overtimeHours = overviewWorkDetails.getOvertimeHours();
        this.weekDays = overviewWorkDetails.getWeekDays();
        this.weekendDays = overviewWorkDetails.getWeekendDays();
        this.workedWeekDays = overviewWorkDetails.getWorkedWeekDays();
        this.workedWeekHours = overviewWorkDetails.getWorkedWeekHours();
        this.workedWeekendDays = overviewWorkDetails.getWorkedWeekendDays();
        this.workedWeekendHours = overviewWorkDetails.getWorkedWeekendHours();
        this.hoursPayedPerDay = overviewWorkDetails.getHoursPayedPerDay();
    }

    @Override
    public BigDecimal getHoursPayedPerDay() {
        return hoursPayedPerDay;
    }
}