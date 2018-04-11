/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services;

import com.xumpy.timesheets.controller.model.Overview;
import com.xumpy.timesheets.domain.Jobs;
import com.xumpy.timesheets.domain.JobsGroup;
import com.xumpy.timesheets.controller.model.JobsInJobsGroup;
import java.text.ParseException;
import java.util.List;

/**
 *
 * @author nicom
 */
public interface JobsGroupSrv {
    JobsGroup select(Integer pk_id);
    List<? extends JobsGroup> selectAllJobGroups();
    List<? extends JobsGroup> selectAllOpenJobGroups();
    List<? extends JobsGroup> selectAllGroupsInJobs(List<Jobs> jobs);
    JobsGroup save(JobsGroup jobsGroup);
    void delete(JobsGroup jobsGroup);
    public List<? extends JobsGroup> filterJobsGroupWithJobsInJobsGroup(List<? extends JobsGroup> lstJobsGroup, List<JobsInJobsGroup> lstJobsInJobsGroup);
    public Overview addJobsGroupInOverview(Overview overview, JobsGroup jobsGroup) throws ParseException;
}
