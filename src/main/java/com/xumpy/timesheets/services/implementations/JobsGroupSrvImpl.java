/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services.implementations;

import com.xumpy.timesheets.dao.implementations.JobsGroupDaoImpl;
import com.xumpy.timesheets.domain.Jobs;
import com.xumpy.timesheets.domain.JobsGroup;
import com.xumpy.timesheets.services.JobsGroupSrv;
import com.xumpy.timesheets.services.model.JobsGroupSrvPojo;
import java.util.ArrayList;
import java.util.List;
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
        
        jobsGroupDao.save(jobsGroup);
        
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
    
}
