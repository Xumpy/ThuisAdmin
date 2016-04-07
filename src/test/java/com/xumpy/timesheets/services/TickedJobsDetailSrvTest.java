/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xumpy.timesheets.controller.model.TickedJobsCtrlPojo;
import com.xumpy.timesheets.dao.implementations.JobsDaoImpl;
import com.xumpy.timesheets.dao.implementations.TickedJobsDaoImpl;
import com.xumpy.timesheets.dao.model.JobsDaoPojo;
import com.xumpy.timesheets.dao.model.JobsGroupDaoPojo;
import com.xumpy.timesheets.dao.model.TickedJobsDaoPojo;
import com.xumpy.timesheets.domain.TickedJobs;
import com.xumpy.timesheets.services.model.TickedJobsDetail;
import com.xumpy.utilities.CustomDateUtils;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author nico
 */
@RunWith(MockitoJUnitRunner.class)
public class TickedJobsDetailSrvTest {
    
    @Mock private TickedJobsDaoImpl tickedJobsDao;
    @Mock private JobsDaoImpl jobsDao;
    @InjectMocks private TickedJobsDetailSrv tickedJobsDetailSrv;
    
    @Test
    public void testCalculate() throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        TickedJobsCtrlPojo tickedJobs1 = Mockito.mock(TickedJobsCtrlPojo.class);
        TickedJobsCtrlPojo tickedJobs2 = Mockito.mock(TickedJobsCtrlPojo.class);
        TickedJobsCtrlPojo tickedJobs3 = Mockito.mock(TickedJobsCtrlPojo.class);
        TickedJobsCtrlPojo tickedJobs4 = Mockito.mock(TickedJobsCtrlPojo.class);
        TickedJobsCtrlPojo tickedJobs5 = Mockito.mock(TickedJobsCtrlPojo.class);
        TickedJobsCtrlPojo tickedJobs6 = Mockito.mock(TickedJobsCtrlPojo.class);
        
        Mockito.when(tickedJobs1.getTicked()).thenReturn(format.parse("2015-07-16 08:00:00"));
        Mockito.when(tickedJobs1.isStarted()).thenReturn(true);
        Mockito.when(tickedJobs2.getTicked()).thenReturn(format.parse("2015-07-16 12:00:00"));
        Mockito.when(tickedJobs2.isStarted()).thenReturn(false);
        Mockito.when(tickedJobs3.getTicked()).thenReturn(format.parse("2015-07-16 12:15:00"));
        Mockito.when(tickedJobs3.isStarted()).thenReturn(true);
        Mockito.when(tickedJobs4.getTicked()).thenReturn(format.parse("2015-07-16 16:30:00"));
        Mockito.when(tickedJobs4.isStarted()).thenReturn(false);
        Mockito.when(tickedJobs5.getTicked()).thenReturn(format.parse("2015-07-16 16:45:00"));
        Mockito.when(tickedJobs5.isStarted()).thenReturn(true);
        Mockito.when(tickedJobs6.getTicked()).thenReturn(format.parse("2015-07-16 20:00:00"));
        Mockito.when(tickedJobs6.isStarted()).thenReturn(false);
        
        List<TickedJobsCtrlPojo> tickedJobs = new ArrayList<TickedJobsCtrlPojo>();
        tickedJobs.add(tickedJobs1);
        tickedJobs.add(tickedJobs2);
        tickedJobs.add(tickedJobs3);
        tickedJobs.add(tickedJobs4);
        tickedJobs.add(tickedJobs5);
        tickedJobs.add(tickedJobs6);
        
        TickedJobsDetail tickedJobsDetail = TickedJobsDetailSrv.calculate(tickedJobs);
        
        Assert.assertEquals(new BigDecimal(30), tickedJobsDetail.getActualPause());
        Assert.assertEquals(new BigDecimal(690), tickedJobsDetail.getActualWorked());
    }
    
    @Test
    public void testCalculate2() throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        TickedJobsCtrlPojo tickedJobs1 = Mockito.mock(TickedJobsCtrlPojo.class);
        TickedJobsCtrlPojo tickedJobs2 = Mockito.mock(TickedJobsCtrlPojo.class);
        TickedJobsCtrlPojo tickedJobs3 = Mockito.mock(TickedJobsCtrlPojo.class);
        TickedJobsCtrlPojo tickedJobs4 = Mockito.mock(TickedJobsCtrlPojo.class);
        
        Mockito.when(tickedJobs1.getTicked()).thenReturn(format.parse("2015-07-16 07:44:00"));
        Mockito.when(tickedJobs1.isStarted()).thenReturn(true);
        Mockito.when(tickedJobs2.getTicked()).thenReturn(format.parse("2015-07-16 12:06:00"));
        Mockito.when(tickedJobs2.isStarted()).thenReturn(false);
        Mockito.when(tickedJobs3.getTicked()).thenReturn(format.parse("2015-07-16 12:29:00"));
        Mockito.when(tickedJobs3.isStarted()).thenReturn(true);
        Mockito.when(tickedJobs4.getTicked()).thenReturn(format.parse("2015-07-16 16:09:00"));
        Mockito.when(tickedJobs4.isStarted()).thenReturn(false);
        
        List<TickedJobsCtrlPojo> tickedJobs = new ArrayList<TickedJobsCtrlPojo>();
        tickedJobs.add(tickedJobs1);
        tickedJobs.add(tickedJobs2);
        tickedJobs.add(tickedJobs3);
        tickedJobs.add(tickedJobs4);
        
        TickedJobsDetail tickedJobsDetail = TickedJobsDetailSrv.calculate(tickedJobs);
        
        Assert.assertEquals(new BigDecimal(23), tickedJobsDetail.getActualPause());
        Assert.assertEquals(new BigDecimal(482), tickedJobsDetail.getActualWorked());
    }
    
    @Test
    public void testCalculate3() throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        TickedJobsCtrlPojo tickedJobs1 = Mockito.mock(TickedJobsCtrlPojo.class);
        TickedJobsCtrlPojo tickedJobs2 = Mockito.mock(TickedJobsCtrlPojo.class);
        TickedJobsCtrlPojo tickedJobs3 = Mockito.mock(TickedJobsCtrlPojo.class);
        TickedJobsCtrlPojo tickedJobs4 = Mockito.mock(TickedJobsCtrlPojo.class);
        
        Mockito.when(tickedJobs1.getTicked()).thenReturn(format.parse("2015-07-16 07:44:00"));
        Mockito.when(tickedJobs1.isStarted()).thenReturn(true);
        Mockito.when(tickedJobs2.getTicked()).thenReturn(format.parse("2015-07-16 12:06:00"));
        Mockito.when(tickedJobs2.isStarted()).thenReturn(false);
        Mockito.when(tickedJobs3.getTicked()).thenReturn(format.parse("2015-07-16 12:29:00"));
        Mockito.when(tickedJobs3.isStarted()).thenReturn(true);
        Mockito.when(tickedJobs4.getTicked()).thenReturn(format.parse("2015-07-16 16:09:00"));
        Mockito.when(tickedJobs4.isStarted()).thenReturn(false);
        
        List<TickedJobsCtrlPojo> tickedJobs = new ArrayList<TickedJobsCtrlPojo>();
        tickedJobs.add(tickedJobs1);
        tickedJobs.add(tickedJobs2);
        tickedJobs.add(tickedJobs3);
        tickedJobs.add(tickedJobs4);
        
        TickedJobsDetail tickedJobsDetail = TickedJobsDetailSrv.calculate(tickedJobs, new BigDecimal(30));
        
        Assert.assertEquals(new BigDecimal(30), tickedJobsDetail.getActualPause());
        Assert.assertEquals(new BigDecimal(475), tickedJobsDetail.getActualWorked());
    }
    
    @Test
    public void testCalculate4() throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        TickedJobsCtrlPojo tickedJobs1 = Mockito.mock(TickedJobsCtrlPojo.class);
        TickedJobsCtrlPojo tickedJobs2 = Mockito.mock(TickedJobsCtrlPojo.class);
        TickedJobsCtrlPojo tickedJobs3 = Mockito.mock(TickedJobsCtrlPojo.class);
        TickedJobsCtrlPojo tickedJobs4 = Mockito.mock(TickedJobsCtrlPojo.class);
        
        Mockito.when(tickedJobs1.getTicked()).thenReturn(format.parse("2015-07-16 07:44:00"));
        Mockito.when(tickedJobs1.isStarted()).thenReturn(true);
        Mockito.when(tickedJobs2.getTicked()).thenReturn(format.parse("2015-07-16 12:06:00"));
        Mockito.when(tickedJobs2.isStarted()).thenReturn(false);
        Mockito.when(tickedJobs3.getTicked()).thenReturn(format.parse("2015-07-16 12:36:00"));
        Mockito.when(tickedJobs3.isStarted()).thenReturn(true);
        Mockito.when(tickedJobs4.getTicked()).thenReturn(format.parse("2015-07-16 16:09:00"));
        Mockito.when(tickedJobs4.isStarted()).thenReturn(false);
        
        List<TickedJobsCtrlPojo> tickedJobs = new ArrayList<TickedJobsCtrlPojo>();
        tickedJobs.add(tickedJobs1);
        tickedJobs.add(tickedJobs2);
        tickedJobs.add(tickedJobs3);
        tickedJobs.add(tickedJobs4);
        
        TickedJobsDetail tickedJobsDetail = TickedJobsDetailSrv.calculate(tickedJobs, new BigDecimal(30));
        
        Assert.assertEquals(new BigDecimal(30), tickedJobsDetail.getActualPause());
        Assert.assertEquals(new BigDecimal(475), tickedJobsDetail.getActualWorked());
    }
    
    @Test
    public void testCalculate5() throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        TickedJobsCtrlPojo tickedJobs1 = Mockito.mock(TickedJobsCtrlPojo.class);
        TickedJobsCtrlPojo tickedJobs2 = Mockito.mock(TickedJobsCtrlPojo.class);
        TickedJobsCtrlPojo tickedJobs3 = Mockito.mock(TickedJobsCtrlPojo.class);
        TickedJobsCtrlPojo tickedJobs4 = Mockito.mock(TickedJobsCtrlPojo.class);
        TickedJobsCtrlPojo tickedJobs5 = Mockito.mock(TickedJobsCtrlPojo.class);
        TickedJobsCtrlPojo tickedJobs6 = Mockito.mock(TickedJobsCtrlPojo.class);
        TickedJobsCtrlPojo tickedJobs7 = Mockito.mock(TickedJobsCtrlPojo.class);
        TickedJobsCtrlPojo tickedJobs8 = Mockito.mock(TickedJobsCtrlPojo.class);
        
        Mockito.when(tickedJobs1.getTicked()).thenReturn(format.parse("2015-07-16 07:44:00"));
        Mockito.when(tickedJobs1.isStarted()).thenReturn(true);
        Mockito.when(tickedJobs2.getTicked()).thenReturn(format.parse("2015-07-16 12:06:00"));
        Mockito.when(tickedJobs2.isStarted()).thenReturn(false);
        Mockito.when(tickedJobs3.getTicked()).thenReturn(format.parse("2015-07-16 12:36:00"));
        Mockito.when(tickedJobs3.isStarted()).thenReturn(true);
        Mockito.when(tickedJobs4.getTicked()).thenReturn(format.parse("2015-07-16 16:09:00"));
        Mockito.when(tickedJobs4.isStarted()).thenReturn(false);
        Mockito.when(tickedJobs5.getTicked()).thenReturn(format.parse("2015-07-17 07:44:00"));
        Mockito.when(tickedJobs5.isStarted()).thenReturn(true);
        Mockito.when(tickedJobs6.getTicked()).thenReturn(format.parse("2015-07-17 12:06:00"));
        Mockito.when(tickedJobs6.isStarted()).thenReturn(false);
        Mockito.when(tickedJobs7.getTicked()).thenReturn(format.parse("2015-07-17 12:36:00"));
        Mockito.when(tickedJobs7.isStarted()).thenReturn(true);
        Mockito.when(tickedJobs8.getTicked()).thenReturn(format.parse("2015-07-17 16:09:00"));
        Mockito.when(tickedJobs8.isStarted()).thenReturn(false);
        
        List<TickedJobsCtrlPojo> tickedJobs = new ArrayList<TickedJobsCtrlPojo>();
        tickedJobs.add(tickedJobs1);
        tickedJobs.add(tickedJobs2);
        tickedJobs.add(tickedJobs3);
        tickedJobs.add(tickedJobs4);
        tickedJobs.add(tickedJobs5);
        tickedJobs.add(tickedJobs6);
        tickedJobs.add(tickedJobs7);
        tickedJobs.add(tickedJobs8);
        
        TickedJobsDetail tickedJobsDetail = TickedJobsDetailSrv.calculate(tickedJobs, new BigDecimal(30));
        
        Assert.assertEquals(new BigDecimal(950), tickedJobsDetail.getActualWorked());
    }
    
    @Test
    public void testCalculate6() throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        TickedJobsCtrlPojo tickedJobs1 = Mockito.mock(TickedJobsCtrlPojo.class);
        TickedJobsCtrlPojo tickedJobs2 = Mockito.mock(TickedJobsCtrlPojo.class);
        
        Mockito.when(tickedJobs1.getTicked()).thenReturn(format.parse("2015-07-16 07:33:00"));
        Mockito.when(tickedJobs1.isStarted()).thenReturn(true);
        Mockito.when(tickedJobs2.getTicked()).thenReturn(format.parse("2015-07-16 18:04:00"));
        Mockito.when(tickedJobs2.isStarted()).thenReturn(false);
        
        List<TickedJobsCtrlPojo> tickedJobs = new ArrayList<TickedJobsCtrlPojo>();
        tickedJobs.add(tickedJobs1);
        tickedJobs.add(tickedJobs2);
        
        TickedJobsDetail tickedJobsDetail = TickedJobsDetailSrv.calculate(tickedJobs, new BigDecimal(30));
        
        Assert.assertEquals(new BigDecimal(30), tickedJobsDetail.getActualPause());
        Assert.assertEquals(new BigDecimal(601), tickedJobsDetail.getActualWorked());
    }

    @Test
    public void testCalculate7Under6HoursNoPauseDetected() throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        TickedJobsCtrlPojo tickedJobs1 = Mockito.mock(TickedJobsCtrlPojo.class);
        TickedJobsCtrlPojo tickedJobs2 = Mockito.mock(TickedJobsCtrlPojo.class);
        
        Mockito.when(tickedJobs1.getTicked()).thenReturn(format.parse("2015-07-16 12:33:00"));
        Mockito.when(tickedJobs1.isStarted()).thenReturn(true);
        Mockito.when(tickedJobs2.getTicked()).thenReturn(format.parse("2015-07-16 18:04:00"));
        Mockito.when(tickedJobs2.isStarted()).thenReturn(false);
        
        List<TickedJobsCtrlPojo> tickedJobs = new ArrayList<TickedJobsCtrlPojo>();
        tickedJobs.add(tickedJobs1);
        tickedJobs.add(tickedJobs2);
        
        TickedJobsDetail tickedJobsDetail = TickedJobsDetailSrv.calculate(tickedJobs, new BigDecimal(30));
        
        Assert.assertEquals(new BigDecimal(0), tickedJobsDetail.getActualPause());
        Assert.assertEquals(new BigDecimal(331), tickedJobsDetail.getActualWorked());
    }
    
    @Test
    public void tickedOverviewMonthTest() throws ParseException, JsonProcessingException{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        JobsGroupDaoPojo jobsGroup = Mockito.mock(JobsGroupDaoPojo.class);
        JobsDaoPojo job1 = Mockito.mock(JobsDaoPojo.class);
        JobsDaoPojo job2 = Mockito.mock(JobsDaoPojo.class);
        
        List<TickedJobsDaoPojo> tickedJobs1 = new ArrayList<TickedJobsDaoPojo>();
        TickedJobsDaoPojo tickedJob1 = Mockito.mock(TickedJobsDaoPojo.class);
        TickedJobsDaoPojo tickedJob2 = Mockito.mock(TickedJobsDaoPojo.class);
        TickedJobsDaoPojo tickedJob3 = Mockito.mock(TickedJobsDaoPojo.class);
        TickedJobsDaoPojo tickedJob4 = Mockito.mock(TickedJobsDaoPojo.class);
        
        List<TickedJobsDaoPojo> tickedJobs2 = new ArrayList<TickedJobsDaoPojo>();
        TickedJobsDaoPojo tickedJob5 = Mockito.mock(TickedJobsDaoPojo.class);
        TickedJobsDaoPojo tickedJob6 = Mockito.mock(TickedJobsDaoPojo.class);
        TickedJobsDaoPojo tickedJob7 = Mockito.mock(TickedJobsDaoPojo.class);
        TickedJobsDaoPojo tickedJob8 = Mockito.mock(TickedJobsDaoPojo.class);
        
        when(job1.getWorkedHours()).thenReturn(new BigDecimal(8));
        when(job2.getWorkedHours()).thenReturn(new BigDecimal(8));
        
        when(tickedJob1.getTicked()).thenReturn(format.parse("2015-06-12 07:33:00"));
        when(tickedJob1.isStarted()).thenReturn(true);
        
        when(tickedJob2.getTicked()).thenReturn(format.parse("2015-06-12 12:01:00"));
        when(tickedJob2.isStarted()).thenReturn(false);
        
        when(tickedJob3.getTicked()).thenReturn(format.parse("2015-06-12 12:18:00"));
        when(tickedJob3.isStarted()).thenReturn(true);
        
        when(tickedJob4.getTicked()).thenReturn(format.parse("2015-06-12 15:54:00"));
        when(tickedJob4.isStarted()).thenReturn(false);
        
        // TickedJobs1 => 471
        
        when(tickedJob5.getTicked()).thenReturn(format.parse("2015-06-15 07:42:00"));
        when(tickedJob5.isStarted()).thenReturn(true);
        
        when(tickedJob6.getTicked()).thenReturn(format.parse("2015-06-15 12:29:00"));
        when(tickedJob6.isStarted()).thenReturn(false);
        
        when(tickedJob7.getTicked()).thenReturn(format.parse("2015-06-15 12:50:00"));
        when(tickedJob7.isStarted()).thenReturn(true);
        
        when(tickedJob8.getTicked()).thenReturn(format.parse("2015-06-15 16:03:00"));
        when(tickedJob8.isStarted()).thenReturn(false);
        
        when(job1.getJobsGroup()).thenReturn(jobsGroup);
        when(job2.getJobsGroup()).thenReturn(jobsGroup);
        
        // TickedJobs2 => 471
        
        List<JobsDaoPojo> jobs = new ArrayList<JobsDaoPojo>();
        jobs.add(job1);
        jobs.add(job2);
        when(jobsDao.selectPeriode(CustomDateUtils.getFirstDayOfMonth("07/2015"), CustomDateUtils.getLastDayOfMonth("07/2015"))).thenReturn(jobs);
        
        tickedJobs1.add(tickedJob1);
        tickedJobs1.add(tickedJob2);
        tickedJobs1.add(tickedJob3);
        tickedJobs1.add(tickedJob4);
        when(tickedJobsDao.selectTickedJobsByJob(job1.getPk_id())).thenReturn(tickedJobs1);
        
        tickedJobs2.add(tickedJob5);
        tickedJobs2.add(tickedJob6);
        tickedJobs2.add(tickedJob7);
        tickedJobs2.add(tickedJob8);
        when(tickedJobsDao.selectTickedJobsByJob(job2.getPk_id())).thenReturn(tickedJobs2);
        
        
        Map<String, String> result = new HashMap<String, String>();
        result.put("actualWorked", "942");
        result.put("timesheetWorked", "960");
        
        for(Entry entry: tickedJobsDetailSrv.tickedOverviewMonth("07/2015").entrySet()){
            assertEquals(result, entry.getValue());
        }
    }
}
