/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.dao;

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

/**
 *
 * @author nicom
 */
@RunWith(MockitoJUnitRunner.class)
public class JobsDaoTest extends Setup{
    @Test
    public void testSelect(){
        Jobs jobs = jobsDao.select(1);
        
        assertEquals("JIRA-TEST1", jobs.getRemarks());
    }
    
    @Test
    public void testInsert(){
        JobsDaoPojo jobs = new JobsDaoPojo();
        jobs.setPk_id(101);
        jobs.setJobsGroup(new JobsGroupDaoPojo(jobsGroupDao.select(1)));
        jobs.setWorkedHours(new BigDecimal(7.4));
        jobs.setRemarks("New Test");
        
        jobsDao.save(jobs);

        Jobs jobsSelect = jobsDao.select(101);
        
        assertEquals("New Test", jobsSelect.getRemarks());
    }
    
    @Test
    public void testUpdate(){
        JobsDaoPojo jobsSelect1 = new JobsDaoPojo(jobsDao.select(1));
        
        jobsSelect1.setRemarks("New Test");
        jobsDao.save(jobsSelect1);
        
        JobsDaoPojo jobsSelect2 = new JobsDaoPojo(jobsDao.select(1));
        
        assertEquals("New Test", jobsSelect2.getRemarks());
    }
    
    @Test
    public void testDelete(){
        Jobs jobsSelect1 = jobsDao.select(1);
        resetTransaction();
        
        jobsDao.delete(jobsSelect1);
        
        jobsSelect1 = jobsDao.select(1);
        
        assertNull(jobsSelect1);
    }
    
    @Test
    public void testSelectDate() throws ParseException{
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date date = df.parse("2015/03/23");
        List<Jobs> jobs = jobsDao.selectDate(date);
        
        assertEquals(new Integer(4), jobs.get(0).getPk_id());
        assertEquals(new Integer(5), jobs.get(1).getPk_id());
    }
    
    @Test
    public void testSelectPeriode() throws ParseException{
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date startDate = df.parse("2015/03/20");
        Date endDate = df.parse("2015/03/23");
        List<Jobs> jobs = jobsDao.selectPeriode(startDate, endDate);
       
        assertEquals(new Integer(1), jobs.get(0).getPk_id());
        assertEquals(new Integer(2), jobs.get(1).getPk_id());
        assertEquals(new Integer(3), jobs.get(2).getPk_id());
        assertEquals(new Integer(4), jobs.get(3).getPk_id());
        assertEquals(new Integer(5), jobs.get(4).getPk_id());
    }
    
    @Test
    public void testGetNewPkId(){
        assertEquals(new Integer(18), jobsDao.getNewPkId());
    }
}
