/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.controller.model;

import java.util.List;

/**
 *
 * @author nicom
 */
public class OverviewWorkHeader {
    private String beginMonth;
    private String endMonth;
    private List<JobsGroupCtrl> jobsGroup;

    public String getBeginMonth() {
        return beginMonth;
    }

    public void setBeginMonth(String beginMonth) {
        this.beginMonth = beginMonth;
    }

    public String getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
    }

    public List<JobsGroupCtrl> getJobsGroup() {
        return jobsGroup;
    }

    public void setJobsGroup(List<JobsGroupCtrl> jobsGroup) {
        this.jobsGroup = jobsGroup;
    }
}
