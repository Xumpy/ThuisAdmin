/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services.model;

import com.xumpy.timesheets.domain.Jobs;
import com.xumpy.timesheets.domain.JobsGroup;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author nicom
 */
public class JobsSrvPojo implements Jobs, Serializable  {
    private Integer pk_id;
    private JobsGroupSrvPojo jobsGroup;
    private Date jobDate;
    private String workedHours;
    private String remarks;
    private String jobDay;
    
    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    public void setJobsGroup(JobsGroupSrvPojo jobsGroup) {
        this.jobsGroup = jobsGroup;
    }

    public void setJobDate(Date jobDate) {
        this.jobDate = jobDate;
    }

    public void setWorkedHours(String workedHours) {
        this.workedHours = workedHours;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    @Override
    public Date getJobDate() {
        return jobDate;
    }

    @Override
    public JobsGroup getJobsGroup() {
        return jobsGroup;
    }

    @Override
    public Integer getPk_id() {
        return pk_id;
    }

    @Override
    public BigDecimal getWorkedHours() {
        return workedHours != null ? new BigDecimal(workedHours): null;
    }

    @Override
    public String getRemarks() {
        return remarks;
    }

    public String getJobDay() {
        return jobDay;
    }

    public void setJobDay(String jobDay) {
        this.jobDay = jobDay;
    }
    
    public JobsSrvPojo(){};
    
    public JobsSrvPojo(Jobs job){
        this.jobDate = job.getJobDate();
        this.jobsGroup = new JobsGroupSrvPojo(job.getJobsGroup());
        this.pk_id = job.getPk_id();
        this.remarks = job.getRemarks();
        System.out.println(job.getClass());
        this.workedHours = job.getWorkedHours() != null ? job.getWorkedHours().toString() : null;
    }
}
