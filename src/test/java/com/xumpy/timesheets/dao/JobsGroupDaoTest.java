/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.dao;

import com.xumpy.Application;
import com.xumpy.security.root.UserService;
import com.xumpy.timesheets.dao.implementations.CompanyDaoImpl;
import com.xumpy.timesheets.dao.implementations.JobsGroupDaoImpl;
import com.xumpy.timesheets.dao.model.CompanyDaoPojo;
import com.xumpy.timesheets.dao.model.JobsGroupDaoPojo;
import com.xumpy.timesheets.domain.JobsGroup;
import java.util.List;
import org.hibernate.SessionFactory;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nicom
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("junit-database")
@Sql(scripts="/data.sql")
public class JobsGroupDaoTest{
    @Autowired JobsGroupDaoImpl jobsGroupDao;
    @Autowired CompanyDaoImpl companyDao;
    
    @Transactional
    private CompanyDaoPojo getCompany(){
        return new CompanyDaoPojo(companyDao.findOne(1));
    }
    
    @Test
    @Transactional
    public void testSelect(){
        JobsGroup jobsGroup = jobsGroupDao.findOne(1);
        
        assertEquals("JIRA-TEST1", jobsGroup.getName());
    }
    
    @Test
    @Transactional
    public void testInsert(){
        JobsGroupDaoPojo jobsGroup = new JobsGroupDaoPojo();
        jobsGroup.setPk_id(101);
        jobsGroup.setName("New Test");
        jobsGroup.setCompany(getCompany());
        
        jobsGroupDao.save(jobsGroup);

        JobsGroup jobsGroupSelect = jobsGroupDao.findOne(101);
        
        assertEquals("New Test", jobsGroupSelect.getName());
    }
    
    @Test
    @Transactional
    public void testUpdate(){
        JobsGroupDaoPojo jobsGroupSelect1 = new JobsGroupDaoPojo(jobsGroupDao.findOne(1));
        
        jobsGroupSelect1.setName("New Test");
        jobsGroupDao.save(jobsGroupSelect1);
        
        JobsGroupDaoPojo jobsGroupSelect2 = new JobsGroupDaoPojo(jobsGroupDao.findOne(1));
        
        assertEquals("New Test", jobsGroupSelect2.getName());
    }
    
    @Test
    @Transactional
    public void testDelete(){
        JobsGroupDaoPojo jobsGroup = new JobsGroupDaoPojo();
        jobsGroup.setPk_id(101);
        jobsGroup.setName("New Test");
        jobsGroup.setCompany(getCompany());
        
        jobsGroupDao.save(jobsGroup);
        
        JobsGroupDaoPojo jobsGroupSelect1 = jobsGroupDao.findOne(101);
        
        assertEquals(jobsGroup.getPk_id(), jobsGroupSelect1.getPk_id());
        
        jobsGroupDao.delete(jobsGroupSelect1);
        
        jobsGroupSelect1 = jobsGroupDao.findOne(101);
        
        assertNull(jobsGroupSelect1);
    }
    
    @Test
    @Transactional
    public void testSelectAllJobGroups(){
        List<JobsGroupDaoPojo> jobsGroup = jobsGroupDao.selectAllJobGroups();
        
        assertTrue(jobsGroup.size() > 1);
    }
    
    @Test
    @Transactional
    public void testGetNewPkId(){
        assertEquals(new Integer(4), jobsGroupDao.getNewPkId());
    }
}
