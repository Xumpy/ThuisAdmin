/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services;

import com.xumpy.timesheets.controller.model.TickedJobsCtrlPojo;
import com.xumpy.timesheets.domain.TickedJobs;
import com.xumpy.timesheets.services.model.TickedJobsDetail;
import com.xumpy.timesheets.services.model.TickedJobsSrvPojo;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author nico
 */
@RunWith(MockitoJUnitRunner.class)
public class TickedJobsDetailSrvTest {
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
}
