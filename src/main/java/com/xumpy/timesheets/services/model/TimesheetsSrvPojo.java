/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services.model;

import com.xumpy.timesheets.domain.JobsGroup;
import com.xumpy.timesheets.domain.Timesheets;
import java.util.Date;

/**
 *
 * @author nico
 */
public class TimesheetsSrvPojo implements Timesheets{
    private Integer pk_id;
    private JobsGroupSrvPojo jobGroup;
    private Date beginDate;
    private Date endDate;
    private byte[] document;
    private String documentMime;
    private String documentName;

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    public void setJobGroup(JobsGroupSrvPojo jobGroup) {
        this.jobGroup = jobGroup;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    public void setDocumentMime(String documentMime) {
        this.documentMime = documentMime;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }
            
    @Override
    public Integer getPk_id() {
        return this.pk_id;
    }

    @Override
    public JobsGroup getJobGroup() {
        return this.jobGroup;
    }

    @Override
    public Date getBeginDate() {
        return this.beginDate;
    }

    @Override
    public Date getEndDate() {
        return this.endDate;
    }

    @Override
    public byte[] getDocument() {
        return this.document;
    }

    @Override
    public String getDocumentMime() {
        return this.documentMime;
    }

    @Override
    public String getDocumentName() {
        return this.documentName;
    }
    
    public TimesheetsSrvPojo(){}
    
    public TimesheetsSrvPojo(Timesheets timesheets){
        this.pk_id = timesheets.getPk_id();
        this.jobGroup = new JobsGroupSrvPojo(timesheets.getJobGroup());
        this.beginDate = timesheets.getBeginDate();
        this.endDate = timesheets.getEndDate();
        this.document = timesheets.getDocument();
        this.documentMime = timesheets.getDocumentMime();
        this.documentName = timesheets.getDocumentName();
    }
}
