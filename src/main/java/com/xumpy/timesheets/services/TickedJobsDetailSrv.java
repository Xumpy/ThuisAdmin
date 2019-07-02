/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services;

import com.xumpy.timesheets.controller.model.JobVatCompensationCtrlPojo;
import com.xumpy.timesheets.dao.implementations.JobVatCompensationDaoImpl;
import com.xumpy.timesheets.dao.implementations.JobsDaoImpl;
import com.xumpy.timesheets.dao.implementations.TickedJobsDaoImpl;
import com.xumpy.timesheets.domain.JobVatCompensation;
import com.xumpy.timesheets.domain.Jobs;
import com.xumpy.timesheets.domain.JobsGroup;
import com.xumpy.timesheets.domain.TickedJobs;
import com.xumpy.timesheets.services.model.JobsGroupSrvPojo;
import com.xumpy.timesheets.services.model.TickedJobsDetail;
import com.xumpy.utilities.CustomDateUtils;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nico
 */
@Service
public class TickedJobsDetailSrv {
    
    @Autowired private TickedJobsDaoImpl tickedJobsDao;
    @Autowired private JobsDaoImpl jobsDao;
    @Autowired private JobVatCompensationDaoImpl jobVatCompensationDao;

    private static final Integer HOURS6 = 360 * 60;

    public static TickedJobsDetail calculate(List<? extends TickedJobs> tickedJobs){
        TickedJobsDetail tickedJobsDetail = new TickedJobsDetail();
        tickedJobsDetail.setTickedJobs(tickedJobs);
        
        if (tickedJobs.size() > 0){
            tickedJobs = sortTickedJobs(tickedJobs);

            Date startCounterWork = null;
            Date startCounterPause = null;

            BigDecimal workedSeconds = new BigDecimal(0);
            BigDecimal pausedSeconds = new BigDecimal(0);

            for(int i = 0; i < tickedJobs.size(); i++){
                if (i%2 == 0){
                    if (!tickedJobs.get(i).isStarted()){
                        tickedJobsDetail.setActualPause(new BigDecimal(-1));
                        tickedJobsDetail.setActualWorked(new BigDecimal(-1));
                        break;
                    } else {
                        if (startCounterPause != null){
                            Seconds seconds = Seconds.secondsBetween(new DateTime(startCounterPause), new DateTime(tickedJobs.get(i).getTicked()));

                            pausedSeconds = pausedSeconds.add(new BigDecimal(seconds.getSeconds()));
                        }
                        startCounterWork = tickedJobs.get(i).getTicked();
                    }
                } else {
                    if (tickedJobs.get(i).isStarted()){
                        tickedJobsDetail.setActualPause(new BigDecimal(-1));
                        tickedJobsDetail.setActualWorked(new BigDecimal(-1));
                        break;
                    } else {
                        Seconds seconds = Seconds.secondsBetween(new DateTime(startCounterWork), new DateTime(tickedJobs.get(i).getTicked()));
                        workedSeconds = workedSeconds.add(new BigDecimal(seconds.getSeconds()));
                        startCounterPause = tickedJobs.get(i).getTicked();
                    }
                }

                tickedJobsDetail.setActualPause(pausedSeconds);
                tickedJobsDetail.setActualWorked(workedSeconds);
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
    
    public static TickedJobsDetail calculate(List<? extends TickedJobs> tickedJobs, List<? extends JobVatCompensation> jobVatCompensations, BigDecimal minimumPause){
        TickedJobsDetail tickedJobsDetail = calculate(tickedJobs);
        List<JobVatCompensationCtrlPojo> jobVatCompensationCtrlPojos = new ArrayList<>();
        for(JobVatCompensation jobVatCompensation: jobVatCompensations){
            jobVatCompensationCtrlPojos.add(new JobVatCompensationCtrlPojo(jobVatCompensation));
        }
        tickedJobsDetail.setJobVatCompensations(jobVatCompensationCtrlPojos);
        if (!tickedJobsDetail.getActualPause().equals(new BigDecimal(0)) || tickedJobsDetail.getActualWorked().compareTo(new BigDecimal(HOURS6)) > 0){
            if (tickedJobsDetail.getActualPause() != null){
                BigDecimal pauseDifference = tickedJobsDetail.getActualPause().subtract(minimumPause);

                if (pauseDifference.compareTo(new BigDecimal(0)) < 0){
                    tickedJobsDetail.setActualPause(minimumPause);
                    tickedJobsDetail.setActualWorked(tickedJobsDetail.getActualWorked().add(pauseDifference));
                }
            }
        }
        
        return tickedJobsDetail;
    }
    
    @Transactional(value="transactionManager")
    public Map<JobsGroupSrvPojo, Map<String, String>> tickedOverviewMonth(String month) throws ParseException{
        List<? extends Jobs> jobs = jobsDao.selectPeriode(CustomDateUtils.getFirstDayOfMonth(month), CustomDateUtils.getLastDayOfMonth(month));
        
        List<JobsGroup> jobsGroups = new ArrayList<JobsGroup>();
        for(Jobs job: jobs){
            if (!jobsGroups.contains(job.getJobsGroup())){
                jobsGroups.add(job.getJobsGroup());
            }
        }
        
        Map<JobsGroupSrvPojo, Map<String, String>> returnMap = new HashMap<JobsGroupSrvPojo, Map<String, String>>();
        
        for(JobsGroup jobsGroup: jobsGroups){
            BigDecimal actualWorked = new BigDecimal(0);
            BigDecimal timesheetWorked = new BigDecimal(0);
            
            Map worked = new HashMap<String, String>();
            
            for(Jobs job: jobs){
                if (jobsGroup.getPk_id().equals(job.getJobsGroup().getPk_id())){
                    List<? extends TickedJobs> tickedJobs = tickedJobsDao.selectTickedJobsByJob(job.getPk_id());
                    List<? extends JobVatCompensation> jobVatCompensations = jobVatCompensationDao.selectJobVatCompensations(job.getPk_id());

                    TickedJobsDetail jobsDetail = calculate(tickedJobs, jobVatCompensations, new BigDecimal(30 * 60));

                    timesheetWorked = timesheetWorked.add(job.getWorkedHours());
                    actualWorked = actualWorked.add(jobsDetail.getActualWorked());
                }
            }
            worked.put("actualWorked", actualWorked.toString());
            worked.put("timesheetWorked", timesheetWorked.multiply(new BigDecimal(60 * 60)).toString());
            
            returnMap.put(new JobsGroupSrvPojo(jobsGroup), worked);
        }
        
        return returnMap;
    }
}
