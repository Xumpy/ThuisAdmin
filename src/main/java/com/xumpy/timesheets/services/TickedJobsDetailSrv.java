/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services;

import com.xumpy.timesheets.dao.JobsDao;
import com.xumpy.timesheets.dao.TickedJobsDao;
import com.xumpy.timesheets.domain.Jobs;
import com.xumpy.timesheets.domain.TickedJobs;
import com.xumpy.timesheets.services.model.TickedJobsDetail;
import com.xumpy.utilities.CustomDateUtils;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nico
 */
@Service
public class TickedJobsDetailSrv {
    
    @Autowired private TickedJobsDao tickedJobsDao;
    @Autowired private JobsDao jobsDao;
    
    public static TickedJobsDetail calculate(List<? extends TickedJobs> tickedJobs){
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
    
    private static List<? extends TickedJobs> sortTickedJobs(List<? extends TickedJobs> tickedJobs){
        Collections.sort(tickedJobs, new Comparator<TickedJobs>() {
            @Override
            public int compare(TickedJobs o1, TickedJobs o2) {
                return o1.getTicked().compareTo(o2.getTicked());
            }
        });
        
        return tickedJobs;
    }
    
    public static TickedJobsDetail calculate(List<? extends TickedJobs> tickedJobs, BigDecimal minimumPause){
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
    
    @Transactional
    public Map<String, String> tickedOverviewMonth(String month) throws ParseException{
        List<Jobs> jobs = jobsDao.selectPeriode(CustomDateUtils.getFirstDayOfMonth(month), CustomDateUtils.getLastDayOfMonth(month));
        
        BigDecimal actualWorked = new BigDecimal(0);
        BigDecimal timesheetWorked = new BigDecimal(0);
        for(Jobs job: jobs){
            List<TickedJobs> tickedJobs = tickedJobsDao.selectTickedJobsByJob(job);
            
            TickedJobsDetail jobsDetail = calculate(tickedJobs, new BigDecimal(30));
            
            timesheetWorked = timesheetWorked.add(job.getWorkedHours());
            actualWorked = actualWorked.add(jobsDetail.getActualWorked());
        }
        
        Map<String, String> returnMap = new HashMap<String, String>();
        
        returnMap.put("actualWorked", actualWorked.toString());
        returnMap.put("timesheetWorked", timesheetWorked.toString());
        
        return returnMap;
    }
}
