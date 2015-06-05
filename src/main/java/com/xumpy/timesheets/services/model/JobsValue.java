/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services.model;

import com.xumpy.timesheets.domain.Jobs;
import java.math.BigDecimal;

/**
 *
 * @author nicom
 */
public class JobsValue {
    private Jobs jobs;
    private BigDecimal actualWorkHours;
    private BigDecimal overtimeHours;
    private BigDecimal overtimeHoursPayed;
    
    public Jobs getJobs() {
        return jobs;
    }

    public void setJobs(Jobs jobs) {
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
