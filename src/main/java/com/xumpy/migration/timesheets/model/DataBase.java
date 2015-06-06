/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.migration.timesheets.model;

import java.util.List;

/**
 *
 * @author nico
 */
public class DataBase {
    public List<JobsGroupPojo> jobsGroup;
    public List<JobsPojo> jobs;

    public List<JobsGroupPojo> getJobsGroup() {
        return jobsGroup;
    }

    public void setJobsGroup(List<JobsGroupPojo> jobsGroup) {
        this.jobsGroup = jobsGroup;
    }

    public List<JobsPojo> getJobs() {
        return jobs;
    }

    public void setJobs(List<JobsPojo> jobs) {
        this.jobs = jobs;
    }
    
}
