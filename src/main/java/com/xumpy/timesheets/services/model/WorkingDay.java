/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services.model;

import com.xumpy.timesheets.domain.Jobs;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author nicom
 */
public class WorkingDay {
    private List<Jobs> jobs;
    private Date date;
    private BigDecimal actualWorkHours;
    private BigDecimal overtimeHours;
    private BigDecimal overtimeHoursPayed;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getOvertimeHoursPayed() {
        return overtimeHoursPayed;
    }

    public void setOvertimeHoursPayed(BigDecimal overtimeHoursPayed) {
        this.overtimeHoursPayed = overtimeHoursPayed;
    }
    
    public List<Jobs> getJobs() {
        return jobs;
    }

    public void setJobs(List<Jobs> jobs) {
        this.jobs = jobs;
    }

    public BigDecimal getActualWorkHours() {
        return actualWorkHours;
    }

    public void setActualWorkHours(BigDecimal actualWorkHours) {
        this.actualWorkHours = actualWorkHours;
    }

    public BigDecimal getOvertimeHours() {
        return overtimeHours;
    }

    public void setOvertimeHours(BigDecimal overtimeHours) {
        this.overtimeHours = overtimeHours;
    }
    
}
