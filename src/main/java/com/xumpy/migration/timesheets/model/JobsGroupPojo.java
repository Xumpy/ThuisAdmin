/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.migration.timesheets.model;

import com.xumpy.timesheets.domain.Company;
import com.xumpy.timesheets.domain.JobsGroup;

/**
 *
 * @author nico
 */
public class JobsGroupPojo implements JobsGroup{
    private Integer pk_id;
    private String name;
    private String description;
    private Company company;

    public void setCompany(Company company) {
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

    @Override
    public Company getCompany() {
        return this.company;
    }
    
}
