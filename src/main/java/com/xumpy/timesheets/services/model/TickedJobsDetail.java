/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services.model;

import com.xumpy.timesheets.controller.model.TickedJobsCtrlPojo;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author nico
 */
public class TickedJobsDetail implements Serializable {
    private List<TickedJobsCtrlPojo> tickedJobs;
    private BigDecimal actualPause;
    private BigDecimal actualWorked;

    public List<TickedJobsCtrlPojo> getTickedJobs() {
        return tickedJobs;
    }

    public void setTickedJobs(List<TickedJobsCtrlPojo> tickedJobs) {
        this.tickedJobs = tickedJobs;
    }

    public BigDecimal getActualPause() {
        return actualPause;
    }

    public void setActualPause(BigDecimal actualPause) {
        this.actualPause = actualPause;
    }

    public BigDecimal getActualWorked() {
        return actualWorked;
    }

    public void setActualWorked(BigDecimal actualWorked) {
        this.actualWorked = actualWorked;
    }
    
}
