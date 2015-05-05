/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services;

import com.xumpy.timesheets.dao.implementations.JobsGroupDaoImpl;
import com.xumpy.timesheets.domain.Jobs;
import com.xumpy.timesheets.domain.JobsGroup;
import com.xumpy.timesheets.services.implementations.JobsGroupSrvImpl;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author nicom
 */
@RunWith(MockitoJUnitRunner.class)
public class JobsGroupSrvTest {
    @Mock JobsGroupDaoImpl jobsGroupDao;
    @Mock JobsGroup jobsGroup;
    
    @InjectMocks JobsGroupSrvImpl jobsGroupSrv;
    
    @Test
    public void testSelect(){
        when(jobsGroupDao.select(1)).thenReturn(jobsGroup);
        assertEquals(jobsGroup, jobsGroupSrv.select(1));
    }
    
    @Test
    public void testSelectAllJobGroups(){
        List<JobsGroup> lstJobsGroups = new ArrayList<JobsGroup>();
        lstJobsGroups.add(jobsGroup);
        lstJobsGroups.add(jobsGroup);
        lstJobsGroups.add(jobsGroup);
        lstJobsGroups.add(jobsGroup);
        
        when(jobsGroupDao.selectAllJobGroups()).thenReturn(lstJobsGroups);
        assertEquals(4, jobsGroupSrv.selectAllJobGroups().size());
    }
    
    @Test
    public void testInsert(){
        when(jobsGroup.getPk_id()).thenReturn(null);
        when(jobsGroup.getName()).thenReturn("Test");
        when(jobsGroup.getDescription()).thenReturn("Test");
        
        jobsGroup = jobsGroupSrv.save(jobsGroup);
        
        assertNotNull(jobsGroup.getPk_id());
    }
    
    @Test
    public void testUpdate(){
        when(jobsGroup.getPk_id()).thenReturn(1);
        when(jobsGroup.getName()).thenReturn("Test");
        when(jobsGroup.getDescription()).thenReturn("Test");
        
        jobsGroup = jobsGroupSrv.save(jobsGroup);
        
        assertEquals(new Integer(1), jobsGroup.getPk_id());
    }
    
    @Test
    public void testDelete(){
        jobsGroupSrv.delete(jobsGroup);
    }
    
    @Test
    public void testSelectAllGroupsInJobs(){
        Jobs jobs1 = Mockito.mock(Jobs.class);
        Jobs jobs2 = Mockito.mock(Jobs.class);
        Jobs jobs3 = Mockito.mock(Jobs.class);
        List<Jobs> lstJobs = new ArrayList<Jobs>();
        lstJobs.add(jobs1);
        lstJobs.add(jobs2);
        lstJobs.add(jobs3);
        
        JobsGroup jobsGroup1 = Mockito.mock(JobsGroup.class);
        JobsGroup jobsGroup2 = Mockito.mock(JobsGroup.class);
        List<JobsGroup> lstJobsGroup = new ArrayList<JobsGroup>();
        lstJobsGroup.add(jobsGroup1);
        lstJobsGroup.add(jobsGroup2);
        
        when(jobs1.getJobsGroup()).thenReturn(jobsGroup1);
        when(jobs2.getJobsGroup()).thenReturn(jobsGroup2);
        when(jobs3.getJobsGroup()).thenReturn(jobsGroup2);
        
        assertEquals(lstJobsGroup, jobsGroupSrv.selectAllGroupsInJobs(lstJobs));
    }
}
