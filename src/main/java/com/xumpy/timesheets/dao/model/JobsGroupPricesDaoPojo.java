/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.dao.model;

import com.xumpy.timesheets.domain.JobsGroup;
import com.xumpy.timesheets.domain.JobsGroupPrices;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author nicom
 */
@Entity
@Table(name="TA_JOBS_GROUP_PRICES")
public class JobsGroupPricesDaoPojo implements JobsGroupPrices, Serializable{
    @Id
    @Column(name="PK_ID")
    private Integer pk_id;
    
    @ManyToOne
    @JoinColumn(name="FK_JOB_GROUP_ID")
    private JobsGroupDaoPojo jobsGroup;
    
    @Column(name="START_DATE")
    private Date startDate;
    
    @Column(name="END_DATE")
    private Date endDate;
    
    @Column(name="PRICE_PER_HOUR")
    private BigDecimal pricePerHour;

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    public void setJobsGroup(JobsGroupDaoPojo jobsGroup) {
        this.jobsGroup = jobsGroup;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setPricePerHour(BigDecimal pricePerHour) {
        this.pricePerHour = pricePerHour;
    }
    
    @Override
    public Integer getPk_id() {
        return pk_id;
    }

    @Override
    public JobsGroup getJobsGroup() {
        return jobsGroup;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    @Override
    public BigDecimal getPricePerHour() {
        return pricePerHour;
    }
    
    public JobsGroupPricesDaoPojo() { }
    
    public JobsGroupPricesDaoPojo(JobsGroupPrices jobsGroupPrices){
        this.endDate = jobsGroupPrices.getEndDate();
        this.jobsGroup = new JobsGroupDaoPojo(jobsGroupPrices.getJobsGroup());
        this.pk_id = jobsGroupPrices.getPk_id();
        this.pricePerHour = jobsGroupPrices.getPricePerHour();
        this.startDate = jobsGroupPrices.getStartDate();
    }
}
