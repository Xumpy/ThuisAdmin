/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services.implementations;

import com.xumpy.timesheets.controller.model.Overview;
import com.xumpy.timesheets.dao.implementations.JobsGroupDaoImpl;
import com.xumpy.timesheets.domain.Jobs;
import com.xumpy.timesheets.domain.JobsGroup;
import com.xumpy.timesheets.services.JobsGroupSrv;
import com.xumpy.timesheets.services.model.JobsGroupSrvPojo;
import com.xumpy.timesheets.services.model.JobsInJobsGroup;
import com.xumpy.timesheets.services.model.JobsSrvPojo;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nicom
 */
@Service
public class JobsGroupSrvImpl implements JobsGroupSrv{

    @Autowired JobsGroupDaoImpl jobsGroupDao;
    
    @Override
    @Transactional(readOnly=false)
    public JobsGroup select(Integer pk_id) {
        return jobsGroupDao.select(pk_id);
    }

    @Override
    @Transactional(readOnly=false)
    public List<JobsGroup> selectAllJobGroups() {
        return jobsGroupDao.selectAllJobGroups();
    }

    @Override
    @Transactional(readOnly=false)
    public JobsGroup save(JobsGroup jobsGroup) {
        JobsGroupSrvPojo jobsGroupSrvPojo = new JobsGroupSrvPojo(jobsGroup);
        
        if (jobsGroupSrvPojo.getPk_id() == null){
            jobsGroupSrvPojo.setPk_id(jobsGroupDao.getNewPkId());
        }
        
        jobsGroupDao.save(jobsGroupSrvPojo);
        
        return jobsGroupSrvPojo;
    }

    @Override
    @Transactional(readOnly=false)
    public void delete(JobsGroup jobsGroup) {
        jobsGroupDao.delete(jobsGroup);
    }

    @Override
    public List<JobsGroup> selectAllGroupsInJobs(List<Jobs> jobs) {
        List<JobsGroup> lstJobsGroup = new ArrayList<JobsGroup>();
        
        for(Jobs job: jobs){
            if (!lstJobsGroup.contains(job.getJobsGroup())){
                lstJobsGroup.add(job.getJobsGroup());
            }
        }
        
        return lstJobsGroup;
    }

    @Override
    public List<JobsGroup> filterJobsGroupWithJobsInJobsGroup(List<JobsGroup> lstJobsGroup, List<JobsInJobsGroup> lstJobsInJobsGroup) {
        List<JobsGroup> lstJobsGroupResult = new ArrayList<JobsGroup>();
        
        for (JobsGroup jobsGroup: lstJobsGroup){
            if (!jobsInJobsGroupContainsJobsGroup(lstJobsInJobsGroup, jobsGroup)){
                lstJobsGroupResult.add(jobsGroup);
            }
        }
        
        return lstJobsGroupResult;
    }

    public boolean jobsInJobsGroupContainsJobsGroup(List<JobsInJobsGroup> lstJobsInJobsGroup, JobsGroup jobsGroup1) {
        for (JobsInJobsGroup jobsInJobsGroup: lstJobsInJobsGroup){
            if (jobsInJobsGroup.getPk_id().equals(jobsGroup1.getPk_id())){
                return true;
            }
        }
        return false;
    }

    @Override
    public Overview addJobsGroupInOverview(Overview overview, JobsGroup jobsGroup) throws ParseException {
        JobsInJobsGroup jobsInJobsGroup = new JobsInJobsGroup();
        
        List<? extends Jobs> jobs = JobsSrvImpl.addZeroDates(new ArrayList<Jobs>(), overview.getMonth(), jobsGroup);
        jobsInJobsGroup.setJobs(JobsSrvPojo.lstJobsSrvPojo(jobs));
        
        jobsInJobsGroup.setPk_id(jobsGroup.getPk_id());
        jobsInJobsGroup.setName(jobsGroup.getName());
        jobsInJobsGroup.setDescription(jobsGroup.getDescription());
        
        overview.getAllJobsInJobsGroup().add(jobsInJobsGroup);
        
        return overview;
    }
    
}
