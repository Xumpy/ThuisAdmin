/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.migration.timesheets.model;

import com.xumpy.timesheets.domain.Jobs;
import com.xumpy.timesheets.domain.JobsGroup;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author nico
 */
public class JobsPojo implements Jobs{

    private Integer pk_id;
    private Integer fk_jobs_group_id;
    private Date jobDate;
    private BigDecimal workedHours;
    private String remarks;
    private BigDecimal percentage;

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }
    
    public Integer getFk_jobs_group_id() {
        return fk_jobs_group_id;
    }

    public void setFk_jobs_group_id(Integer fk_jobs_group_id) {
        this.fk_jobs_group_id = fk_jobs_group_id;
    }

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    public void setJobDate(Date jobDate) {
        this.jobDate = jobDate;
    }

    public void setWorkedHours(BigDecimal workedHours) {
        this.workedHours = workedHours;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    @Override
    public Date getJobDate() {
        return this.jobDate;
    }

    @Override
    public JobsGroup getJobsGroup() {
        return null;
    }

    @Override
    public Integer getPk_id() {
        return this.pk_id;
    }

    @Override
    public BigDecimal getWorkedHours() {
        return this.workedHours;
    }

    @Override
    public String getRemarks() {
        return this.remarks;
    }

    @Override
    public BigDecimal getPercentage() {
        return this.percentage;
    }
    
}
