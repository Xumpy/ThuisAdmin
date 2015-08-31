/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services.model;

import com.xumpy.timesheets.domain.Company;
import com.xumpy.timesheets.domain.JobsGroup;
import java.io.Serializable;

/**
 *
 * @author nicom
 */
public class JobsGroupSrvPojo implements JobsGroup, Serializable{
    private Integer pk_id;
    private String name;
    private String description;
    private CompanySrvPojo company;

    public void setCompany(CompanySrvPojo company) {
        this.company = company;
    }
    
    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getPk_id() {
        return pk_id;
    }
    
    public JobsGroupSrvPojo(){}
    
    public JobsGroupSrvPojo(JobsGroup jobsGroup){
        this.description = jobsGroup.getDescription();
        this.name = jobsGroup.getName();
        this.pk_id = jobsGroup.getPk_id();
        this.company = jobsGroup.getCompany() == null ? null : new CompanySrvPojo(jobsGroup.getCompany());
    }

    @Override
    public Company getCompany() {
        return company;
    }
    
}
