/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.dao;

import com.xumpy.security.SpringConfig;
import com.xumpy.security.root.InitDatabase;
import com.xumpy.security.root.InitOldDatabase;
import com.xumpy.security.root.UserService;
import com.xumpy.security.servlet.DispatcherConfig;
import com.xumpy.timesheets.dao.implementations.JobsDaoImpl;
import com.xumpy.timesheets.dao.implementations.JobsGroupDaoImpl;
import com.xumpy.timesheets.dao.model.JobsDaoPojo;
import com.xumpy.timesheets.dao.model.JobsGroupDaoPojo;
import com.xumpy.timesheets.domain.Jobs;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nicom
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {InitDatabase.class, InitOldDatabase.class, UserService.class})
@ActiveProfiles("junit")
public class JobsDaoTest{
    @Autowired public JobsGroupDaoImpl jobsGroupDao;
    @Autowired public JobsDaoImpl jobsDao;
    
    @Test
    @Transactional(value="jpaTransactionManager")
    public void testSelect(){
        Jobs jobs = jobsDao.findOne(1);
        
        assertEquals("JIRA-TEST1", jobs.getRemarks());
    }
    
    @Test
    @Transactional(value="jpaTransactionManager")
    public void testInsert(){
        JobsDaoPojo jobs = new JobsDaoPojo();
        jobs.setPk_id(101);
        jobs.setJobsGroup(new JobsGroupDaoPojo(jobsGroupDao.findOne(1)));
        jobs.setWorkedHours(new BigDecimal(7.4));
        jobs.setRemarks("New Test");
        
        jobsDao.save(jobs);

        Jobs jobsSelect = jobsDao.findOne(101);
        
        assertEquals("New Test", jobsSelect.getRemarks());
    }
    
    @Test
    @Transactional(value="jpaTransactionManager")
    public void testUpdate(){
        JobsDaoPojo jobsSelect1 = new JobsDaoPojo(jobsDao.findOne(1));
        
        jobsSelect1.setRemarks("New Test");
        jobsDao.save(jobsSelect1);
        
        JobsDaoPojo jobsSelect2 = new JobsDaoPojo(jobsDao.findOne(1));
        
        assertEquals("New Test", jobsSelect2.getRemarks());
    }
    
    @Test
    @Transactional(value="jpaTransactionManager")
    public void testDelete(){
        Jobs jobsSelect1 = jobsDao.findOne(1);
        //resetTransaction();
        
        jobsDao.delete(new JobsDaoPojo(jobsSelect1));
        
        jobsSelect1 = jobsDao.findOne(1);
        
        assertNull(jobsSelect1);
    }
    
    @Test
    @Transactional(value="jpaTransactionManager")
    public void testSelectDate() throws ParseException{
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date date = df.parse("2015/03/23");
        List<? extends Jobs> jobs = jobsDao.selectDate(date);
        
        assertEquals(new Integer(4), jobs.get(0).getPk_id());
        assertEquals(new Integer(5), jobs.get(1).getPk_id());
    }
    
    @Test
    @Transactional
    public void testSelectPeriode() throws ParseException{
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date startDate = df.parse("2015/03/20");
        Date endDate = df.parse("2015/03/23");
        List<? extends Jobs> jobs = jobsDao.selectPeriode(startDate, endDate);
       
        assertEquals(new Integer(1), jobs.get(0).getPk_id());
        assertEquals(new Integer(2), jobs.get(1).getPk_id());
        assertEquals(new Integer(3), jobs.get(2).getPk_id());
        assertEquals(new Integer(4), jobs.get(3).getPk_id());
        assertEquals(new Integer(5), jobs.get(4).getPk_id());
    }
    
    @Test
    @Transactional
    public void testGetNewPkId(){
        assertEquals(new Integer(18), jobsDao.getNewPkId());
    }
}
