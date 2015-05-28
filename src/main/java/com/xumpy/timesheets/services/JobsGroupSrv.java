/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services;

import com.xumpy.timesheets.controller.model.Overview;
import com.xumpy.timesheets.domain.Jobs;
import com.xumpy.timesheets.domain.JobsGroup;
import com.xumpy.timesheets.services.model.JobsInJobsGroup;
import java.text.ParseException;
import java.util.List;

/**
 *
 * @author nicom
 */
public interface JobsGroupSrv {
    JobsGroup select(Integer pk_id);
    List<JobsGroup> selectAllJobGroups();
    List<JobsGroup> selectAllGroupsInJobs(List<Jobs> jobs);
    JobsGroup save(JobsGroup jobsGroup);
    void delete(JobsGroup jobsGroup);
    public List<JobsGroup> filterJobsGroupWithJobsInJobsGroup(List<JobsGroup> lstJobsGroup, List<JobsInJobsGroup> lstJobsInJobsGroup);
    public Overview addJobsGroupInOverview(Overview overview, JobsGroup jobsGroup) throws ParseException;
}
