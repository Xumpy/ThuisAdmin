/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services;

import com.xumpy.timesheets.domain.TickedJobs;
import com.xumpy.timesheets.services.model.TickedJobsDetail;
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
        
        TickedJobs tickedJobs1 = Mockito.mock(TickedJobs.class);
        TickedJobs tickedJobs2 = Mockito.mock(TickedJobs.class);
        TickedJobs tickedJobs3 = Mockito.mock(TickedJobs.class);
        TickedJobs tickedJobs4 = Mockito.mock(TickedJobs.class);
        TickedJobs tickedJobs5 = Mockito.mock(TickedJobs.class);
        TickedJobs tickedJobs6 = Mockito.mock(TickedJobs.class);
        
        Mockito.when(tickedJobs1.getTicked()).thenReturn(format.parse("2015-07-16 08:00:00"));
        Mockito.when(tickedJobs2.getTicked()).thenReturn(format.parse("2015-07-16 12:00:00"));
        Mockito.when(tickedJobs3.getTicked()).thenReturn(format.parse("2015-07-16 12:15:00"));
        Mockito.when(tickedJobs4.getTicked()).thenReturn(format.parse("2015-07-16 16:30:00"));
        Mockito.when(tickedJobs5.getTicked()).thenReturn(format.parse("2015-07-16 16:45:00"));
        Mockito.when(tickedJobs6.getTicked()).thenReturn(format.parse("2015-07-16 20:00:00"));
        
        List<TickedJobs> tickedJobs = new ArrayList<TickedJobs>();
        tickedJobs.add(tickedJobs1);
        tickedJobs.add(tickedJobs2);
        tickedJobs.add(tickedJobs3);
        tickedJobs.add(tickedJobs4);
        tickedJobs.add(tickedJobs5);
        tickedJobs.add(tickedJobs6);
        
        TickedJobsDetail tickedJobsDetail = TickedJobsDetailSrv.calculate(tickedJobs);
        
        Assert.assertEquals(new BigDecimal(30), tickedJobsDetail.getActualPause());
        Assert.assertEquals(new BigDecimal(690), tickedJobsDetail.getActualPause());
    }
}
