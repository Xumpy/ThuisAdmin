/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.controller.model;

import com.xumpy.timesheets.domain.Company;
import com.xumpy.timesheets.domain.JobsGroup;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nicom
 */
public class JobsGroupCtrl implements JobsGroup, Serializable{
    private Integer pk_id;
    private String name;
    private String description;
    private Integer checked;
    private CompanyCtrlPojo company;

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

    public void setChecked(Integer checked) {
        this.checked = checked;
    }
    
    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Integer getPk_id() {
        return this.pk_id;
    }

    public Integer getChecked() {
        return checked;
    }
    
    public JobsGroupCtrl(){}
    
    public JobsGroupCtrl(JobsGroup jobsGroup){
        this.pk_id = jobsGroup.getPk_id();
        this.name = jobsGroup.getName();
        this.description = jobsGroup.getDescription();
        this.checked = 0;
        this.company = jobsGroup.getCompany() == null ? null : new CompanyCtrlPojo(jobsGroup.getCompany());
    }
    
    public static List<JobsGroupCtrl> allJobsGroupCtrl(List<JobsGroup> allJobsGroup){
        List<JobsGroupCtrl> lstJobsGroupCtrl = new ArrayList<JobsGroupCtrl>();
        
        for (JobsGroup jobsGroup: allJobsGroup){
            lstJobsGroupCtrl.add(new JobsGroupCtrl(jobsGroup));
        }
        
        return lstJobsGroupCtrl;
    }

    @Override
    public Company getCompany() {
        return this.company;
    }
}
