package com.xumpy.timesheets.controller.model;

import com.xumpy.government.controllers.model.VatCompensationCtrlPojo;
import com.xumpy.government.domain.VatCompensation;
import com.xumpy.timesheets.domain.JobVatCompensation;
import com.xumpy.timesheets.domain.Jobs;

import java.math.BigDecimal;

public class JobVatCompensationCtrlPojo implements JobVatCompensation {

    private Integer pkId;
    private JobsCtrlPojo job;
    private VatCompensationCtrlPojo vatCompensation;
    private BigDecimal amount;
    private byte[] document;
    private String documentName;
    private String documentMime;

    @Override
    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    @Override
    public Jobs getJob() {
        return job;
    }

    public void setJob(JobsCtrlPojo job) {
        this.job = job;
    }

    @Override
    public VatCompensation getVatCompensation() {
        return vatCompensation;
    }

    public void setVatCompensation(VatCompensationCtrlPojo vatCompensation) {
        this.vatCompensation = vatCompensation;
    }

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    @Override
    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    @Override
    public String getDocumentMime() {
        return documentMime;
    }

    public void setDocumentMime(String documentMime) {
        this.documentMime = documentMime;
    }

    public JobVatCompensationCtrlPojo() {}

    public JobVatCompensationCtrlPojo(JobVatCompensation jobVatCompensation){
        this.pkId = jobVatCompensation.getPkId();
        this.job = jobVatCompensation.getJob() == null ? null : new JobsCtrlPojo(jobVatCompensation.getJob());
        this.vatCompensation = jobVatCompensation.getVatCompensation() == null ? null : new VatCompensationCtrlPojo(jobVatCompensation.getVatCompensation());
        this.amount = jobVatCompensation.getAmount();
    }
}
