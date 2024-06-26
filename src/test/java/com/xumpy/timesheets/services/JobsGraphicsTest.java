/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services;

import com.xumpy.timesheets.services.implementations.JobsGraphics;
import com.xumpy.timesheets.dao.implementations.JobsDaoImpl;
import com.xumpy.timesheets.dao.model.CompanyDaoPojo;
import com.xumpy.timesheets.dao.model.JobsDaoPojo;
import com.xumpy.timesheets.dao.model.JobsGroupDaoPojo;
import com.xumpy.timesheets.domain.Jobs;
import com.xumpy.timesheets.domain.JobsGroup;
import com.xumpy.timesheets.domain.OverviewWork.OverviewWork;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JobsGraphicsTest {
    
    @Mock JobsDaoImpl jobsDao;
    @Mock CompanyDaoPojo company;
    
    @InjectMocks JobsGraphics jobsGraphics;
    
    private List<JobsDaoPojo> jobs = new ArrayList<JobsDaoPojo>();
    
    @Before
    public void setUp() throws ParseException{
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        
        JobsDaoPojo job1 = Mockito.mock(JobsDaoPojo.class);
        JobsDaoPojo job2 = Mockito.mock(JobsDaoPojo.class);
        JobsDaoPojo job3 = Mockito.mock(JobsDaoPojo.class);
        JobsDaoPojo job4 = Mockito.mock(JobsDaoPojo.class);
        JobsDaoPojo job5 = Mockito.mock(JobsDaoPojo.class);
        JobsDaoPojo job6 = Mockito.mock(JobsDaoPojo.class);
        
        JobsGroup jobsGroup1 = Mockito.mock(JobsGroup.class);
        JobsGroup jobsGroup2 = Mockito.mock(JobsGroup.class);
       
        when(company.getDailyPayedHours()).thenReturn(new BigDecimal(7.6));
        
        when(jobsGroup1.getPk_id()).thenReturn(1);
        when(jobsGroup2.getPk_id()).thenReturn(2);
        when(jobsGroup1.getCompany()).thenReturn(company);
        when(jobsGroup2.getCompany()).thenReturn(company);
        
        when(job1.getJobDate()).thenReturn(df.parse("01/01/2015"));
        when(job2.getJobDate()).thenReturn(df.parse("04/01/2015"));
        when(job3.getJobDate()).thenReturn(df.parse("20/02/2015"));
        when(job4.getJobDate()).thenReturn(df.parse("21/02/2015"));
        when(job5.getJobDate()).thenReturn(df.parse("01/03/2015"));
        when(job6.getJobDate()).thenReturn(df.parse("31/03/2015"));
        
        JobsGroupDaoPojo jobsGroupDaoPojo1 = new JobsGroupDaoPojo(jobsGroup1);
        JobsGroupDaoPojo jobsGroupDaoPojo2 = new JobsGroupDaoPojo(jobsGroup2);
        
        when(job1.getJobsGroup()).thenReturn(jobsGroupDaoPojo1);
        when(job2.getJobsGroup()).thenReturn(jobsGroupDaoPojo1);
        when(job3.getJobsGroup()).thenReturn(jobsGroupDaoPojo2);
        when(job4.getJobsGroup()).thenReturn(jobsGroupDaoPojo2);
        when(job5.getJobsGroup()).thenReturn(jobsGroupDaoPojo1);
        when(job6.getJobsGroup()).thenReturn(jobsGroupDaoPojo1);
        
        when(job1.getWorkedHours()).thenReturn(new BigDecimal(8));
        when(job2.getWorkedHours()).thenReturn(new BigDecimal(4));
        when(job3.getWorkedHours()).thenReturn(new BigDecimal(10));
        when(job4.getWorkedHours()).thenReturn(new BigDecimal(5));
        when(job5.getWorkedHours()).thenReturn(new BigDecimal(4));
        when(job6.getWorkedHours()).thenReturn(new BigDecimal(8));
        
        jobs.add(job1);
        jobs.add(job2);
        jobs.add(job3);
        jobs.add(job4);
        jobs.add(job5);
        jobs.add(job6);
        
        when(jobsDao.selectPeriode(df.parse("01/01/2015"), df.parse("31/03/2015"))).thenReturn(jobs);
    }
    
    @Test
    public void testGetJobsInMonth() throws ParseException{
        List<Jobs> jobsResult = jobsGraphics.getJobsInMonth(jobs, "01/2015");
        assertEquals(2, jobsResult.size());
        assertEquals(2, jobsResult.size());
    }
    
    @Test
    public void testOverviewWorkTest1() throws ParseException{
        MathContext mc = new MathContext(3, RoundingMode.HALF_UP);
        
        JobsGroup jobsGroup1 = Mockito.mock(JobsGroup.class);
        JobsGroup jobsGroup2 = Mockito.mock(JobsGroup.class);

        when(jobsGroup1.getPk_id()).thenReturn(1);
        when(jobsGroup2.getPk_id()).thenReturn(2);
        
        List<JobsGroup> jobsGroups = new ArrayList<JobsGroup>();
        jobsGroups.add(jobsGroup1);
        jobsGroups.add(jobsGroup2);
        
        OverviewWork overviewWork = jobsGraphics.overviewWork("01/2015", "03/2015", jobsGroups);
        
        
        assertEquals(new BigDecimal(7.6), overviewWork.getMonthlyWorkDetails().get(0).getWorkDetails().getHoursPayedPerDay());
        
        assertEquals(new BigDecimal(8), overviewWork.getMonthlyWorkDetails().get(0).getWorkDetails().getWorkedWeekHours());
        assertEquals(new BigDecimal(4), overviewWork.getMonthlyWorkDetails().get(0).getWorkDetails().getWorkedWeekendHours());
        assertEquals(new BigDecimal(10), overviewWork.getMonthlyWorkDetails().get(1).getWorkDetails().getWorkedWeekHours());
        assertEquals(new BigDecimal(5), overviewWork.getMonthlyWorkDetails().get(1).getWorkDetails().getWorkedWeekendHours());
        assertEquals(new BigDecimal(8), overviewWork.getMonthlyWorkDetails().get(2).getWorkDetails().getWorkedWeekHours());
        assertEquals(new BigDecimal(4), overviewWork.getMonthlyWorkDetails().get(2).getWorkDetails().getWorkedWeekendHours());
        
        assertEquals(new BigDecimal(1), overviewWork.getMonthlyWorkDetails().get(0).getWorkDetails().getWorkedWeekDays());
        assertEquals(new BigDecimal(1), overviewWork.getMonthlyWorkDetails().get(0).getWorkDetails().getWorkedWeekendDays());
        assertEquals(new BigDecimal(1), overviewWork.getMonthlyWorkDetails().get(1).getWorkDetails().getWorkedWeekDays());
        assertEquals(new BigDecimal(1), overviewWork.getMonthlyWorkDetails().get(1).getWorkDetails().getWorkedWeekendDays());
        assertEquals(new BigDecimal(1), overviewWork.getMonthlyWorkDetails().get(2).getWorkDetails().getWorkedWeekDays());
        assertEquals(new BigDecimal(1), overviewWork.getMonthlyWorkDetails().get(2).getWorkDetails().getWorkedWeekendDays());
        
        assertEquals(new BigDecimal(-3.2, mc), overviewWork.getMonthlyWorkDetails().get(0).getWorkDetails().getOvertimeHours());
        assertEquals(new BigDecimal(-0.2, mc), overviewWork.getMonthlyWorkDetails().get(1).getWorkDetails().getOvertimeHours());
        assertEquals(new BigDecimal(-3.2, mc), overviewWork.getMonthlyWorkDetails().get(2).getWorkDetails().getOvertimeHours());
    }
    
    @Test
    public void testOverviewWorkTest2() throws ParseException{
        JobsGroup jobsGroup1 = Mockito.mock(JobsGroup.class);
        
        when(jobsGroup1.getPk_id()).thenReturn(1);

        List<JobsGroup> jobsGroups = new ArrayList<JobsGroup>();
        jobsGroups.add(jobsGroup1);
        
        OverviewWork overviewWork = jobsGraphics.overviewWork("01/2015", "03/2015", jobsGroups);
        
        assertEquals(new BigDecimal(8), overviewWork.getMonthlyWorkDetails().get(0).getWorkDetails().getWorkedWeekHours());
        assertEquals(new BigDecimal(4), overviewWork.getMonthlyWorkDetails().get(0).getWorkDetails().getWorkedWeekendHours());
        assertEquals(new BigDecimal(0), overviewWork.getMonthlyWorkDetails().get(1).getWorkDetails().getWorkedWeekHours());
        assertEquals(new BigDecimal(0), overviewWork.getMonthlyWorkDetails().get(1).getWorkDetails().getWorkedWeekendHours());
        assertEquals(new BigDecimal(8), overviewWork.getMonthlyWorkDetails().get(2).getWorkDetails().getWorkedWeekHours());
        assertEquals(new BigDecimal(4), overviewWork.getMonthlyWorkDetails().get(2).getWorkDetails().getWorkedWeekendHours());
        
        assertEquals(new BigDecimal(1), overviewWork.getMonthlyWorkDetails().get(0).getWorkDetails().getWorkedWeekDays());
        assertEquals(new BigDecimal(1), overviewWork.getMonthlyWorkDetails().get(0).getWorkDetails().getWorkedWeekendDays());
        assertEquals(new BigDecimal(0), overviewWork.getMonthlyWorkDetails().get(1).getWorkDetails().getWorkedWeekDays());
        assertEquals(new BigDecimal(0), overviewWork.getMonthlyWorkDetails().get(1).getWorkDetails().getWorkedWeekendDays());
        assertEquals(new BigDecimal(1), overviewWork.getMonthlyWorkDetails().get(2).getWorkDetails().getWorkedWeekDays());
        assertEquals(new BigDecimal(1), overviewWork.getMonthlyWorkDetails().get(2).getWorkDetails().getWorkedWeekendDays());
    }
}
