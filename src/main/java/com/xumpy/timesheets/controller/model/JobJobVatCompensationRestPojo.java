package com.xumpy.timesheets.controller.model;

import java.math.BigDecimal;

/**
 * Created by nico on 01/03/2019.
 */
public class JobJobVatCompensationRestPojo {
    private Integer jobId;
    private JobVatCompensationRestPojo jobVatCompensation;
    private BigDecimal amount;
    private String image;

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public JobVatCompensationRestPojo getJobVatCompensation() {
        return jobVatCompensation;
    }

    public void setJobVatCompensation(JobVatCompensationRestPojo jobVatCompensation) {
        this.jobVatCompensation = jobVatCompensation;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
