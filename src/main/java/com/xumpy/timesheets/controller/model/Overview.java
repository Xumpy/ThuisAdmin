/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.controller.model;

import com.xumpy.timesheets.services.model.JobsInJobsGroup;
import java.io.Serializable;
import java.util.List;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author nicom
 */
@Component
@Scope(value="session")
public class Overview implements Serializable{
    private String month;
    private List<JobsInJobsGroup> allJobsInJobsGroup;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<JobsInJobsGroup> getAllJobsInJobsGroup() {
        return allJobsInJobsGroup;
    }

    public void setAllJobsInJobsGroup(List<JobsInJobsGroup> allJobsInJobsGroup) {
        this.allJobsInJobsGroup = allJobsInJobsGroup;
    }
}
