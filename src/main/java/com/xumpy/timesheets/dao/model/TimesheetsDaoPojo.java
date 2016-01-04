/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.dao.model;

import com.xumpy.timesheets.domain.JobsGroup;
import com.xumpy.timesheets.domain.Timesheets;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author nico
 */
@Entity
@Table(name="TA_TIMESHEETS")
public class TimesheetsDaoPojo implements Timesheets{
    @Id
    @Column(name="PK_ID")
    private Integer pk_id;
    
    @ManyToOne
    @JoinColumn(name="FK_JOB_GROUP_ID")
    private JobsGroupDaoPojo jobGroup;
    
    @Column(name="BEGIN_DATE")
    private Date beginDate;
    
    @Column(name="END_DATE")
    private Date endDate;
    
    @Column(name="DOCUMENT")
    private byte[] document;
    
    @Column(name="DOCUMENT_NAME")
    private String documentName;
    
    @Column(name="DOCUMENT_MIME")
    private String documentMime;

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    public void setJobGroup(JobsGroupDaoPojo jobGroup) {
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

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public void setDocumentMime(String documentMime) {
        this.documentMime = documentMime;
    }
    
    @Override
    public Integer getPk_id() {
        return pk_id;
    }

    @Override
    public JobsGroup getJobGroup() {
        return jobGroup;
    }

    @Override
    public Date getBeginDate() {
        return beginDate;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    @Override
    public byte[] getDocument() {
        return document;
    }

    @Override
    public String getDocumentMime() {
        return documentMime;
    }

    @Override
    public String getDocumentName() {
        return documentName;
    }
    
    public TimesheetsDaoPojo() {}
    
    public TimesheetsDaoPojo(Timesheets timesheets){
        this.pk_id = timesheets.getPk_id();
        this.jobGroup = new JobsGroupDaoPojo(timesheets.getJobGroup());
        this.beginDate = timesheets.getBeginDate();
        this.endDate = timesheets.getEndDate();
        this.document = timesheets.getDocument();
        this.documentMime = timesheets.getDocumentMime();
        this.documentName = timesheets.getDocumentName();
    }
}
