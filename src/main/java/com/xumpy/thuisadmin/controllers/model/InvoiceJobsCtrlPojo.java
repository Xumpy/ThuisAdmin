package com.xumpy.thuisadmin.controllers.model;

import com.xumpy.thuisadmin.domain.InvoiceJobs;
import com.xumpy.timesheets.controller.model.JobsCtrlPojo;

import java.math.BigDecimal;

public class InvoiceJobsCtrlPojo implements InvoiceJobs {
    private Integer pkId;
    private JobsCtrlPojo job;
    private InvoicesCtrlPojo invoice;
    private BigDecimal amount;
    private String description;
    private Boolean timeUnitDays;

    @Override
    public Boolean isTimeUnitDays() {
        return timeUnitDays;
    }

    public void setTimeUnitDays(Boolean timeUnitDays) {
        this.timeUnitDays = timeUnitDays;
    }

    @Override
    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    @Override
    public JobsCtrlPojo getJob() {
        return job;
    }

    public void setJob(JobsCtrlPojo job) {
        this.job = job;
    }

    @Override
    public InvoicesCtrlPojo getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoicesCtrlPojo invoice) {
        this.invoice = invoice;
    }

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public InvoiceJobsCtrlPojo() {}
    public InvoiceJobsCtrlPojo(InvoiceJobs invoiceJobs) {
        this.pkId = invoiceJobs.getPkId();
        this.job = invoiceJobs.getJob() != null ? new JobsCtrlPojo(invoiceJobs.getJob()) : null;
        this.invoice = invoiceJobs.getInvoice() != null ? new InvoicesCtrlPojo(invoiceJobs.getInvoice()) : null;
        this.amount = invoiceJobs.getAmount();
        this.description = invoiceJobs.getDescription();
        this.timeUnitDays = invoiceJobs.isTimeUnitDays();
    }
}
