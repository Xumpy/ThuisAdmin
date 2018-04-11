/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services.model;

import com.xumpy.timesheets.domain.JobsGroup;
import com.xumpy.timesheets.domain.JobsGroupPrices;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author nicom
 */
public class JobsGroupPricesSrvPojo implements JobsGroupPrices{
    private Integer pk_id;
    private JobsGroupSrvPojo jobsGroup;
    private Date startDate;
    private Date endDate;
    private BigDecimal pricePerHour;

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    public void setJobsGroup(JobsGroupSrvPojo jobsGroup) {
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
        return this.pk_id;
    }

    @Override
    public JobsGroup getJobsGroup() {
        return this.jobsGroup;
    }

    @Override
    public Date getStartDate() {
        return this.startDate;
    }

    @Override
    public Date getEndDate() {
        return this.endDate;
    }

    @Override
    public BigDecimal getPricePerHour() {
        return this.pricePerHour;
    }
    
    public JobsGroupPricesSrvPojo() { }
    
    public JobsGroupPricesSrvPojo(JobsGroupPrices jobsGroupPrices){
        this.endDate = jobsGroupPrices.getEndDate();
        this.jobsGroup = jobsGroupPrices.getJobsGroup() == null ? null : new JobsGroupSrvPojo(jobsGroupPrices.getJobsGroup());
        this.pk_id = jobsGroupPrices.getPk_id();
        this.pricePerHour = jobsGroupPrices.getPricePerHour();
        this.startDate = jobsGroupPrices.getStartDate();
    }
}
