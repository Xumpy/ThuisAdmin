package com.xumpy.timesheets.controller.model;

import com.xumpy.timesheets.domain.JobsGroupPrices;

import java.math.BigDecimal;
import java.util.Date;

public class JobsGroupPricesCtrlPojo implements JobsGroupPrices{
    private Integer pk_id;
    private JobsGroupCtrl jobsGroup;
    private Date startDate;
    private Date endDate;
    private BigDecimal pricePerHour;

    @Override
    public Integer getPk_id() {
        return pk_id;
    }

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    @Override
    public JobsGroupCtrl getJobsGroup() {
        return jobsGroup;
    }

    public void setJobsGroup(JobsGroupCtrl jobsGroup) {
        this.jobsGroup = jobsGroup;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public BigDecimal getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(BigDecimal pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public JobsGroupPricesCtrlPojo(){}

    public JobsGroupPricesCtrlPojo(JobsGroupPrices jobsGroupPrices){
        this.pk_id = jobsGroupPrices.getPk_id();
        this.endDate = jobsGroupPrices.getEndDate();
        this.startDate = jobsGroupPrices.getStartDate();
        this.jobsGroup = new JobsGroupCtrl(jobsGroupPrices.getJobsGroup());
        this.pricePerHour = jobsGroupPrices.getPricePerHour();
    }
}
