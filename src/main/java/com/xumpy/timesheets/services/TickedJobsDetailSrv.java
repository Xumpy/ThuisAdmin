/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services;

import com.xumpy.timesheets.domain.TickedJobs;
import com.xumpy.timesheets.services.model.TickedJobsDetail;
import com.xumpy.timesheets.services.model.TickedJobsSrvPojo;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Minutes;

/**
 *
 * @author nico
 */
public class TickedJobsDetailSrv {
    public static TickedJobsDetail calculate(List<? extends TickedJobs> tickedJobs){
        TickedJobsDetail tickedJobsDetail = new TickedJobsDetail();
        tickedJobsDetail.setTickedJobs(tickedJobs);
        
        tickedJobs = sortTickedJobs(tickedJobs);
        
        System.out.println(tickedJobs.size());
        
        Date startCounterWork = new Date();
        Date startCounterPause = new Date();
        
        for(int i = 0; i < tickedJobs.size(); i++){
            if (i%2 == 0){
                if (!tickedJobs.get(i).isStarted()){
                    tickedJobsDetail.setActualPause(new BigDecimal(-1));
                    tickedJobsDetail.setActualWorked(new BigDecimal(-1));
                    break;
                } else {
                    if (!startCounterPause.equals(new Date())){
                        Minutes minutes = Minutes.minutesBetween(new DateTime(startCounterPause), new DateTime()); 
                        tickedJobsDetail.setActualPause(new BigDecimal(minutes.getMinutes()));
                    }
                    startCounterWork = tickedJobs.get(i).getTicked();
                }
            } else {
                if (tickedJobs.get(i).isStarted()){
                    tickedJobsDetail.setActualPause(new BigDecimal(-1));
                    tickedJobsDetail.setActualWorked(new BigDecimal(-1));
                    break;
                } else {
                    Minutes minutes = Minutes.minutesBetween(new DateTime(startCounterWork), new DateTime()); 
                    tickedJobsDetail.setActualPause(new BigDecimal(minutes.getMinutes()));
                    startCounterPause = tickedJobs.get(i).getTicked();
                }
            }
        }
        
        return tickedJobsDetail;
    }
    
    private static List<TickedJobs> sortTickedJobs(List<? extends TickedJobs> tickedJobs){
        List<TickedJobs> sortTickedJobs = new ArrayList<TickedJobs>();
        
        Collections.sort(sortTickedJobs, new Comparator<TickedJobs>() {
            @Override
            public int compare(TickedJobs o1, TickedJobs o2) {
                return o1.getTicked().compareTo(o2.getTicked());
            }
        });
        
        return sortTickedJobs;
    }
}
