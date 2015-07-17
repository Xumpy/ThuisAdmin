/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services;

import com.xumpy.timesheets.controller.model.TickedJobsCtrlPojo;
import com.xumpy.timesheets.domain.TickedJobs;
import com.xumpy.timesheets.services.model.TickedJobsDetail;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.Minutes;

/**
 *
 * @author nico
 */
public class TickedJobsDetailSrv {
    public static TickedJobsDetail calculate(List<TickedJobsCtrlPojo> tickedJobs){
        TickedJobsDetail tickedJobsDetail = new TickedJobsDetail();
        tickedJobsDetail.setTickedJobs(tickedJobs);
        
        if (tickedJobs.size() > 0){
            tickedJobs = sortTickedJobs(tickedJobs);

            Date startCounterWork = null;
            Date startCounterPause = null;

            BigDecimal workedMinutes = new BigDecimal(0);
            BigDecimal pausedMinutes = new BigDecimal(0);

            for(int i = 0; i < tickedJobs.size(); i++){
                if (i%2 == 0){
                    if (!tickedJobs.get(i).isStarted()){
                        tickedJobsDetail.setActualPause(new BigDecimal(-1));
                        tickedJobsDetail.setActualWorked(new BigDecimal(-1));
                        break;
                    } else {
                        if (startCounterPause != null){
                            Minutes minutes = Minutes.minutesBetween(new DateTime(startCounterPause), new DateTime(tickedJobs.get(i).getTicked())); 

                            pausedMinutes = pausedMinutes.add(new BigDecimal(minutes.getMinutes()));
                        }
                        startCounterWork = tickedJobs.get(i).getTicked();
                    }
                } else {
                    if (tickedJobs.get(i).isStarted()){
                        tickedJobsDetail.setActualPause(new BigDecimal(-1));
                        tickedJobsDetail.setActualWorked(new BigDecimal(-1));
                        break;
                    } else {
                        Minutes minutes = Minutes.minutesBetween(new DateTime(startCounterWork), new DateTime(tickedJobs.get(i).getTicked()));
                        workedMinutes = workedMinutes.add(new BigDecimal(minutes.getMinutes()));
                        startCounterPause = tickedJobs.get(i).getTicked();
                    }
                }

                tickedJobsDetail.setActualPause(pausedMinutes);
                tickedJobsDetail.setActualWorked(workedMinutes);
            }
        } else {
            tickedJobsDetail.setTickedJobs(tickedJobs);
            tickedJobsDetail.setActualWorked(new BigDecimal(0));
            tickedJobsDetail.setActualPause(new BigDecimal(0));
        }
        
        return tickedJobsDetail;
    }
    
    private static List<TickedJobsCtrlPojo> sortTickedJobs(List<TickedJobsCtrlPojo> tickedJobs){
        Collections.sort(tickedJobs, new Comparator<TickedJobs>() {
            @Override
            public int compare(TickedJobs o1, TickedJobs o2) {
                return o1.getTicked().compareTo(o2.getTicked());
            }
        });
        
        return tickedJobs;
    }
    
    public static TickedJobsDetail calculate(List<TickedJobsCtrlPojo> tickedJobs, BigDecimal minimumPause){
        TickedJobsDetail tickedJobsDetail = calculate(tickedJobs);

        if (tickedJobsDetail.getActualPause() != null){
            BigDecimal pauseDifference = tickedJobsDetail.getActualPause().subtract(minimumPause);

            if (pauseDifference.compareTo(new BigDecimal(0)) < 0){
                tickedJobsDetail.setActualPause(minimumPause);
                tickedJobsDetail.setActualWorked(tickedJobsDetail.getActualWorked().add(pauseDifference));
            }
        }
        
        return tickedJobsDetail;
    }
}
