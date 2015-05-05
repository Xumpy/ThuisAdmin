/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.dao;

import com.xumpy.timesheets.dao.model.JobsGroupDaoPojo;
import com.xumpy.timesheets.domain.JobsGroup;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author nicom
 */
@RunWith(MockitoJUnitRunner.class)
public class JobsGroupDaoTest extends Setup{
    @Test
    public void testSelect(){
        JobsGroup jobsGroup = jobsGroupDao.select(1);
        
        assertEquals("JIRA-TEST1", jobsGroup.getName());
    }
    
    @Test
    public void testInsert(){
        JobsGroupDaoPojo jobsGroup = new JobsGroupDaoPojo();
        jobsGroup.setPk_id(101);
        jobsGroup.setName("New Test");
        
        jobsGroupDao.save(jobsGroup);

        JobsGroup jobsGroupSelect = jobsGroupDao.select(101);
        
        assertEquals("New Test", jobsGroupSelect.getName());
    }
    
    @Test
    public void testUpdate(){
        JobsGroupDaoPojo jobsGroupSelect1 = new JobsGroupDaoPojo(jobsGroupDao.select(1));
        
        jobsGroupSelect1.setName("New Test");
        jobsGroupDao.save(jobsGroupSelect1);
        
        JobsGroupDaoPojo jobsGroupSelect2 = new JobsGroupDaoPojo(jobsGroupDao.select(1));
        
        assertEquals("New Test", jobsGroupSelect2.getName());
    }
    
    @Test
    public void testDelete(){
        JobsGroupDaoPojo jobsGroup = new JobsGroupDaoPojo();
        jobsGroup.setPk_id(101);
        jobsGroup.setName("New Test");
        
        jobsGroupDao.save(jobsGroup);
        
        JobsGroup jobsGroupSelect1 = jobsGroupDao.select(101);
        
        assertEquals(jobsGroup.getPk_id(), jobsGroupSelect1.getPk_id());
        
        resetTransaction();
        
        jobsGroupDao.delete(jobsGroupSelect1);
        
        jobsGroupSelect1 = jobsGroupDao.select(101);
        
        assertNull(jobsGroupSelect1);
    }
    
    @Test
    public void testSelectAllJobGroups(){
        List<JobsGroup> jobsGroup = jobsGroupDao.selectAllJobGroups();
        
        assertTrue(jobsGroup.size() > 1);
    }
    
    @Test
    public void testGetNewPkId(){
        assertEquals(new Integer(4), jobsGroupDao.getNewPkId());
    }
}
