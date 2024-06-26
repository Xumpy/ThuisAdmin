/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services;

import com.xumpy.timesheets.controller.model.Overview;
import com.xumpy.timesheets.dao.implementations.JobsDaoImpl;
import com.xumpy.timesheets.dao.model.CompanyDaoPojo;
import com.xumpy.timesheets.dao.model.JobsDaoPojo;
import com.xumpy.timesheets.dao.model.JobsGroupDaoPojo;
import com.xumpy.timesheets.domain.Jobs;
import com.xumpy.timesheets.domain.JobsGroup;
import com.xumpy.timesheets.services.implementations.JobsGroupSrvImpl;
import com.xumpy.timesheets.services.implementations.JobsSrvImpl;
import com.xumpy.timesheets.services.model.JobsSrvPojo;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

/**
 *
 * @author nicom
 */
@RunWith(MockitoJUnitRunner.class)
public class JobsSrvImplTest {
    @Mock JobsDaoImpl jobsDao;
    @Mock JobsDaoPojo jobs;
    @Mock JobsGroup jobsGroup;
    @Mock Overview overview;
    @Mock CompanyDaoPojo company;
    
    @Spy JobsGroupSrvImpl jobsGroupSrv;
    
    @InjectMocks JobsSrvImpl jobsSrv;
    
    @Test
    public void testSelect(){
        when(jobsDao.findById(1)).thenReturn(Optional.of(jobs));
        assertEquals(jobs, jobsSrv.select(1));
    }
    
    @Test
    public void testSelectDate(){
        Date date = Mockito.mock(Date.class);
        List<JobsDaoPojo> lstJobs = new ArrayList<JobsDaoPojo>();
        lstJobs.add(jobs);
        
        when(jobsDao.selectDate(date)).thenReturn(lstJobs);
        
        assertEquals(lstJobs, jobsSrv.selectDate(date));
    }
    
    @Test
    public void testPelectPeriode(){
        Date startDate = Mockito.mock(Date.class);
        Date endDate = Mockito.mock(Date.class);
        
        List<JobsDaoPojo> lstJobs = new ArrayList<JobsDaoPojo>();
        lstJobs.add(jobs);
        
        when(jobsDao.selectPeriode(startDate, endDate)).thenReturn(lstJobs);
        
        assertEquals(lstJobs, jobsSrv.selectPeriode(startDate, endDate));
    }
    
    @Test
    public void testInsert(){
        Date date = Mockito.mock(Date.class);
        JobsGroup jobsGroup = Mockito.mock(JobsGroup.class);
        when(jobsGroup.getCompany()).thenReturn(company);
        when(company.getPk_id()).thenReturn(1);
        
        when(jobs.getPk_id()).thenReturn(null);
        when(jobs.getJobDate()).thenReturn(date);
        JobsGroupDaoPojo jobsGroupDaoPojo = new JobsGroupDaoPojo(jobsGroup);
        when(jobs.getJobsGroup()).thenReturn(jobsGroupDaoPojo);
        when(jobs.getRemarks()).thenReturn("Test");
        when(jobs.getWorkedHours()).thenReturn(new BigDecimal("7.6"));
        
        jobs = new JobsDaoPojo(jobsSrv.save(jobs));
        
        assertNotNull(jobs.getPk_id());
    }
    
    @Test
    public void testUpdate(){
        Date date = Mockito.mock(Date.class);
        JobsGroup jobsGroup = Mockito.mock(JobsGroup.class);
        when(jobsGroup.getCompany()).thenReturn(company);
        when(company.getPk_id()).thenReturn(1);
        
        when(jobs.getPk_id()).thenReturn(1);
        when(jobs.getJobDate()).thenReturn(date);
        JobsGroupDaoPojo jobsGroupDaoPojo = new JobsGroupDaoPojo(jobsGroup);
        when(jobs.getJobsGroup()).thenReturn(jobsGroupDaoPojo);
        when(jobs.getRemarks()).thenReturn("Test");
        when(jobs.getWorkedHours()).thenReturn(new BigDecimal("7.6"));
        
        jobs = new JobsDaoPojo(jobsSrv.save(jobs));
        
        assertEquals(new Integer(1), jobs.getPk_id());
    }
    
    @Test
    public void testDelete(){
        jobsSrv.delete(jobs);
    }
    
    @Test
    public void testSelectMonth() throws ParseException{
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String month = "05/2015";
        Date startPeriode = df.parse("01/05/2015");
        Date endPeriode = df.parse("31/05/2015");
        List<JobsDaoPojo> lstJobs = new ArrayList<JobsDaoPojo>();
        lstJobs.add(jobs);
        
        when(jobsDao.selectPeriode(startPeriode, endPeriode)).thenReturn(lstJobs);
        List<? extends Jobs> lstJobsResult = jobsSrv.selectMonth(month);
        
        assertEquals(lstJobs, lstJobsResult);
    }
    
    @Test
    public void testSaveJobs(){
        Date date = Mockito.mock(Date.class);
        JobsGroup jobsGroup = Mockito.mock(JobsGroup.class);
        when(jobsGroup.getCompany()).thenReturn(company);
        when(company.getPk_id()).thenReturn(1);
        
        JobsSrvPojo job1 = Mockito.mock(JobsSrvPojo.class);
        JobsSrvPojo job2 = Mockito.mock(JobsSrvPojo.class);
        
        when(job1.getPk_id()).thenReturn(1);
        when(job1.getJobDate()).thenReturn(date);
        when(job1.getJobsGroup()).thenReturn(jobsGroup);
        when(job1.getRemarks()).thenReturn("Test");
        when(job1.getWorkedHours()).thenReturn(new BigDecimal("7.6"));
        
        when(job2.getPk_id()).thenReturn(null);
        when(job2.getJobDate()).thenReturn(date);
        when(job2.getJobsGroup()).thenReturn(jobsGroup);
        when(job2.getRemarks()).thenReturn("Test");
        when(job2.getWorkedHours()).thenReturn(new BigDecimal("7.6"));
        
        List<JobsSrvPojo> lstJobs = new ArrayList<JobsSrvPojo>();
        lstJobs.add(job1);
        lstJobs.add(job2);
        
        List<? extends Jobs> lstJobsResult = jobsSrv.saveJobs(lstJobs);
        
        assertEquals(new Integer(1), lstJobsResult.get(0).getPk_id());
        assertNotNull(lstJobsResult.get(1).getPk_id());
    }
    
    @Test
    public void testSelectPeriodeJobsInJobGroup() throws ParseException{
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date inPeriode = df.parse("01/03/2015");
        Date endPeriode = df.parse("31/03/2015");
        Date outPeriode = df.parse("01/04/2015");
        
        JobsGroup jobsGroup1 = Mockito.mock(JobsGroup.class);
        JobsGroup jobsGroup2 = Mockito.mock(JobsGroup.class);
        when(jobsGroup1.getCompany()).thenReturn(company);
        when(company.getPk_id()).thenReturn(1);
        
        JobsSrvPojo job1 = Mockito.mock(JobsSrvPojo.class);
        JobsSrvPojo job2 = Mockito.mock(JobsSrvPojo.class);
        JobsSrvPojo job3 = Mockito.mock(JobsSrvPojo.class);
        JobsSrvPojo job4 = Mockito.mock(JobsSrvPojo.class);
        
        when(job1.getJobDate()).thenReturn(inPeriode);
        when(job2.getJobDate()).thenReturn(inPeriode);

        when(job1.getJobsGroup()).thenReturn(jobsGroup1);
        when(job2.getJobsGroup()).thenReturn(jobsGroup1);

        List<JobsDaoPojo> periode = new ArrayList<JobsDaoPojo>();
        periode.add(new JobsDaoPojo(job1));
        periode.add(new JobsDaoPojo(job2));
        
        when(jobsDao.selectPeriode(inPeriode, endPeriode)).thenReturn(periode);
        
        List<JobsSrvPojo> jobsInJobsInGroup = new ArrayList<JobsSrvPojo>();
        jobsInJobsInGroup.add(job1);
        jobsInJobsInGroup.add(job2);
        
        assertEquals(2, jobsSrv.selectPeriodeJobsInJobGroup(inPeriode, endPeriode).size());
        assertEquals(inPeriode, jobsSrv.selectPeriodeJobsInJobGroup(inPeriode, endPeriode).get(0).getJobs().get(0).getJobDate());
        assertEquals(inPeriode, jobsSrv.selectPeriodeJobsInJobGroup(inPeriode, endPeriode).get(1).getJobs().get(0).getJobDate());
    }
    
    @Test
    public void testFillMonth() throws ParseException{
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date date = df.parse("01/03/2015");
        when(jobs.getJobDate()).thenReturn(date);
        JobsGroupDaoPojo jobsGroupDaoPojo = new JobsGroupDaoPojo(jobsGroup);
        when(jobs.getJobsGroup()).thenReturn(jobsGroupDaoPojo);

        List<Jobs> allMonthJobs = new ArrayList<Jobs>();
        allMonthJobs.add(jobs);
        
        List<? extends Jobs> fillMonthJobs = jobsSrv.fillMonth(allMonthJobs);
        
        assertEquals(31, fillMonthJobs.size());
        assertEquals("10", ((JobsSrvPojo)fillMonthJobs.get(9)).getJobDay());
        assertEquals(df.parse("10/03/2015"), fillMonthJobs.get(9).getJobDate());
        assertEquals(new BigDecimal(0), fillMonthJobs.get(9).getWorkedHours());
    }
    
    @Test
    public void testFilterMonth() throws ParseException{
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = df.parse("01/03/2015");
        Date date2 = df.parse("02/03/2015");
        Jobs job1 = Mockito.mock(Jobs.class);
        Jobs job2 = Mockito.mock(Jobs.class);
        when(job1.getWorkedHours()).thenReturn(new BigDecimal("7.6"));
        when(job2.getPk_id()).thenReturn(null);
        when(job2.getWorkedHours()).thenReturn(new BigDecimal("0"));
        List<Jobs> process = new ArrayList<Jobs>();
        process.add(job1);
        process.add(job2);
        
        List<Jobs> expected = new ArrayList<Jobs>();
        expected.add(job1);
        
        assertEquals(expected, jobsSrv.filterMonth(process));
    }
    
    @Test
    public void testFillPlusFilterMonth() throws ParseException{
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date date = df.parse("01/03/2015");
        when(jobs.getJobDate()).thenReturn(date);
        when(jobsGroup.getName()).thenReturn("Test");
        JobsGroupDaoPojo jobsGroupDaoPojo = new JobsGroupDaoPojo(jobsGroup);
        when(jobs.getJobsGroup()).thenReturn(jobsGroupDaoPojo);
        when(jobs.getWorkedHours()).thenReturn(new BigDecimal("7.6"));
        
        List<Jobs> allMonthJobs = new ArrayList<Jobs>();
        allMonthJobs.add(jobs);
        
        List<? extends Jobs> fillMonthJobs = jobsSrv.fillMonth(allMonthJobs);
        List<Jobs> filterMonthJobs = jobsSrv.filterMonth(fillMonthJobs);
        
        assertEquals(1, filterMonthJobs.size());
        assertEquals("Test", filterMonthJobs.get(0).getJobsGroup().getName());
        assertEquals(new BigDecimal("7.6"), filterMonthJobs.get(0).getWorkedHours());
    }
    
    @Test
    public void testSelectMonthJobsInJobGroup() throws ParseException{
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date inPeriode = df.parse("01/03/2015");
        Date endPeriode = df.parse("31/03/2015");
        Date outPeriode = df.parse("01/04/2015");
        
        JobsGroup jobsGroup1 = Mockito.mock(JobsGroup.class);
        JobsGroup jobsGroup2 = Mockito.mock(JobsGroup.class);
        when(jobsGroup1.getCompany()).thenReturn(company);
        when(company.getPk_id()).thenReturn(1);
        
        
        Jobs job1 = Mockito.mock(Jobs.class);
        Jobs job2 = Mockito.mock(Jobs.class);
        Jobs job3 = Mockito.mock(Jobs.class);
        Jobs job4 = Mockito.mock(Jobs.class);
        
        when(job1.getJobDate()).thenReturn(inPeriode);
        when(job2.getJobDate()).thenReturn(inPeriode);

        when(jobsGroup1.getName()).thenReturn("Test");

        when(jobsGroup1.getDescription()).thenReturn("Test");

        when(job1.getJobsGroup()).thenReturn(jobsGroup1);
        when(job2.getJobsGroup()).thenReturn(jobsGroup1);

        List<JobsDaoPojo> periode = new ArrayList<JobsDaoPojo>();
        periode.add(new JobsDaoPojo(job1));
        periode.add(new JobsDaoPojo(job2));
        when(jobsDao.selectPeriode(inPeriode, endPeriode)).thenReturn(periode);
        
        assertEquals(31, jobsSrv.selectMonthJobsInJobGroup("03/2015", overview).get(0).getJobs().size());
        assertEquals("Test", jobsSrv.selectMonthJobsInJobGroup("03/2015", overview).get(0).getJobs().get(0).getJobsGroup().getName());
        assertEquals(df.parse("01/03/2015"), jobsSrv.selectMonthJobsInJobGroup("03/2015", overview).get(0).getJobs().get(0).getJobDate());
        assertEquals(df.parse("10/03/2015"), jobsSrv.selectMonthJobsInJobGroup("03/2015", overview).get(0).getJobs().get(9).getJobDate());
    }
    
    @Test
    public void testJobInWeekend() throws ParseException{
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date mondayDate = df.parse("04/05/2015");
        Date wensdayDate = df.parse("06/05/2015");
        Date saterdayDate = df.parse("09/05/2015");
        Date sundayDate = df.parse("10/05/2015");

        Jobs job1 = Mockito.mock(Jobs.class);
        Jobs job2 = Mockito.mock(Jobs.class);
        Jobs job3 = Mockito.mock(Jobs.class);
        Jobs job4 = Mockito.mock(Jobs.class);
        
        when(job1.getJobDate()).thenReturn(mondayDate);
        when(job2.getJobDate()).thenReturn(wensdayDate);
        when(job3.getJobDate()).thenReturn(saterdayDate);
        when(job4.getJobDate()).thenReturn(sundayDate);
        
        assertEquals(false, JobsSrvImpl.jobInWeekend(job1));
        assertEquals(false, JobsSrvImpl.jobInWeekend(job2));
        assertEquals(true, JobsSrvImpl.jobInWeekend(job3));
        assertEquals(true, JobsSrvImpl.jobInWeekend(job4));
    }
}
