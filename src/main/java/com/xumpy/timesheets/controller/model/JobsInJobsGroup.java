/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.controller.model;

import com.xumpy.timesheets.domain.Company;
import com.xumpy.timesheets.domain.JobsGroup;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author nicom
 */
public class JobsInJobsGroup implements JobsGroup, Serializable{
    
    private Integer pk_id;
    private String name;
    private String description;
    private CompanyCtrlPojo company;
    
    private List<JobsCtrlPojo> jobs;

    public void setCompany(CompanyCtrlPojo company) {
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

    public List<JobsCtrlPojo> getJobs() {
        return jobs;
    }

    public void setJobs(List<JobsCtrlPojo> jobs) {
        this.jobs = jobs;
    }
    
    public JobsInJobsGroup(){}
    
    public JobsInJobsGroup(JobsGroup jobsGroup){
        this.description = jobsGroup.getDescription();
        this.name = jobsGroup.getName();
        this.pk_id = jobsGroup.getPk_id();
        this.company = new CompanyCtrlPojo(jobsGroup.getCompany());
    }

    @Override
    public Company getCompany() {
        return this.company;
    }
}
