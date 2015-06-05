/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services.model;

import com.xumpy.timesheets.domain.Jobs;
import com.xumpy.timesheets.domain.JobsGroup;
import com.xumpy.timesheets.services.implementations.JobsSrvImpl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author nicom
 */
public class JobsSrvPojo implements Jobs, Serializable  {
    private Integer pk_id;
    private JobsGroupSrvPojo jobsGroup;
    private boolean weekendDay;
    private Date jobDate;
    private String workedHours;
    private String remarks;
    private String jobDay;
    private BigDecimal percentage;
    
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

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
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

    public boolean isWeekendDay() {
        return weekendDay;
    }

    public void setWeekendDay(boolean weekendDay) {
        this.weekendDay = weekendDay;
    }
    
    public JobsSrvPojo(){};
    
    public JobsSrvPojo(Jobs job){
        this.jobDate = job.getJobDate();
        this.jobsGroup = new JobsGroupSrvPojo(job.getJobsGroup());
        this.pk_id = job.getPk_id();
        this.remarks = job.getRemarks();
        this.workedHours = job.getWorkedHours() != null ? job.getWorkedHours().toString() : null;
        this.weekendDay = JobsSrvImpl.jobInWeekend(job);
        this.percentage = job.getPercentage();
    }
    
    public static List<JobsSrvPojo> lstJobsSrvPojo(List<? extends Jobs> jobs){
        List<JobsSrvPojo> lstJobsSrv = new ArrayList<JobsSrvPojo>();
        for(Jobs job: jobs){
            lstJobsSrv.add(new JobsSrvPojo(job));
        }
        
        return lstJobsSrv;
    }

    @Override
    public BigDecimal getPercentage() {
        return this.percentage;
    }
}
