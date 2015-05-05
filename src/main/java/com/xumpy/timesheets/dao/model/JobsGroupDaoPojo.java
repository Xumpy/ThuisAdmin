/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.dao.model;

import com.xumpy.timesheets.domain.JobsGroup;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
    
    public JobsGroupDaoPojo(JobsGroup jobsGroup){
        this.description = jobsGroup.getDescription();
        this.name = jobsGroup.getName();
        this.pk_id = jobsGroup.getPk_id();
    }
}
