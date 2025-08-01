/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.dao.model;

import com.xumpy.timesheets.domain.Company;
import com.xumpy.timesheets.domain.JobsGroup;
import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 *
 * @author nicom
 */
@Entity
@Table(name="TA_JOB_GROUPS")
public class JobsGroupDaoPojo implements JobsGroup, Serializable {
    @Id
    @Column(name="PK_ID")
    private Integer pk_id;
    
    @Column(name="JOB_NAME")
    private String name;
    
    @Column(name="DESCRIPTION")
    private String description;

    @ManyToOne
    @JoinColumn(name="FK_COMPANY_ID")
    private CompanyDaoPojo company;

    @Column(name="CLOSED")
    private Boolean closed;

    @Column(name="EXTRA_TIME")
    private Integer extraTime;

    @Override
    public Integer getExtraTime() {
        return extraTime;
    }

    public void setExtraTime(Integer extraTime) {
        this.extraTime = extraTime;
    }

    public void setCompany(CompanyDaoPojo company) {
        this.company = company;
    }
    
    @Override
    public Integer getPk_id() {
        return pk_id;
    }

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public JobsGroupDaoPojo(){}

    @Override
    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public JobsGroupDaoPojo(JobsGroup jobsGroup){
        this.description = jobsGroup.getDescription();
        this.name = jobsGroup.getName();
        this.pk_id = jobsGroup.getPk_id();
        this.company = jobsGroup.getCompany() == null ? null : new CompanyDaoPojo(jobsGroup.getCompany());
        this.closed = jobsGroup.getClosed();
        this.extraTime = jobsGroup.getExtraTime();
    }

    @Override
    public Company getCompany() {
        return this.company;
    }
}
