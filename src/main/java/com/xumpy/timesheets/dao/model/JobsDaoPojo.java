/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.dao.model;

import com.xumpy.timesheets.domain.Jobs;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import jakarta.persistence.*;

/**
 *
 * @author nicom
 */
@Entity
@Table(name="TA_JOBS")
public class JobsDaoPojo implements Serializable, Jobs {
    @Id
    @Column(name="PK_ID")
    private Integer pk_id;
    
    @ManyToOne
    @JoinColumn(name="FK_JOB_GROUP_ID")
    private JobsGroupDaoPojo jobsGroup;
    
    @Column(name="JOB_DATE")
    @Temporal(jakarta.persistence.TemporalType.DATE)
    private Date jobDate;
    
    @Column(name="WORKED_HOURS")
    private BigDecimal workedHours;

    @Column(name="REMARKS")
    private String remarks;
    
    @Column(name="PERCENTAGE")
    private BigDecimal percentage;
    
    @Override
    public Integer getPk_id() {
        return pk_id;
    }

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    @Override
    public JobsGroupDaoPojo getJobsGroup() {
        return jobsGroup;
    }

    public void setJobsGroup(JobsGroupDaoPojo jobsGroup) {
        this.jobsGroup = jobsGroup;
    }

    @Override
    public Date getJobDate() {
        return jobDate;
    }

    public void setJobDate(Date jobDate) {
        this.jobDate = jobDate;
    }

    @Override
    public BigDecimal getWorkedHours() {
        return workedHours;
    }

    public void setWorkedHours(BigDecimal workedHours) {
        this.workedHours = workedHours;
    }

    @Override
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    public JobsDaoPojo(){}
    
    public JobsDaoPojo(Jobs jobs){
        this.jobDate = jobs.getJobDate();
        this.jobsGroup = jobs.getJobsGroup() != null ? new JobsGroupDaoPojo(jobs.getJobsGroup()): null;
        this.pk_id = jobs.getPk_id();
        this.workedHours = jobs.getWorkedHours();
        this.remarks = jobs.getRemarks();
        this.percentage = jobs.getPercentage();
    }

    @Override
    public BigDecimal getPercentage() {
        return this.percentage;
    }
}
