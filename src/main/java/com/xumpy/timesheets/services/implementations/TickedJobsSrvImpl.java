/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services.implementations;

import com.xumpy.thuisadmin.dao.sqlite.model.TimeRecording;
import com.xumpy.timesheets.controller.model.JobsCtrlPojo;
import com.xumpy.timesheets.controller.model.TickedJobsCtrlPojo;
import com.xumpy.timesheets.controller.model.TickedJobsLstCtrlPojo;
import com.xumpy.timesheets.dao.implementations.JobsDaoImpl;
import com.xumpy.timesheets.dao.implementations.TickedJobsDaoImpl;
import com.xumpy.timesheets.dao.model.TickedJobsDaoPojo;
import com.xumpy.timesheets.domain.Jobs;
import com.xumpy.timesheets.domain.TickedJobs;
import com.xumpy.timesheets.services.TickedJobsSrv;
import com.xumpy.timesheets.services.model.JobsSrvPojo;
import com.xumpy.timesheets.services.model.TickedJobsSrvPojo;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.xumpy.timesheets.services.session.SessionTimesheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nicom
 */
@Service
public class TickedJobsSrvImpl implements TickedJobsSrv{

    @Autowired TickedJobsDaoImpl tickedJobsDao;
    @Autowired JobsDaoImpl jobsDao;
    @Autowired SessionTimesheet sessionTimesheet;

    @Override
    @Transactional
    public TickedJobs select(Integer pk_id) {
        return tickedJobsDao.findById(pk_id).get();
    }

    @Override
    @Transactional
    public List<? extends TickedJobs> selectAllTickedJobs() {
        return tickedJobsDao.selectAllTickedJobs();
    }

    @Override
    @Transactional
    public TickedJobs save(TickedJobs tickedJobs) {
        TickedJobsSrvPojo tickedJobsSrv = new TickedJobsSrvPojo(tickedJobs);
        if (tickedJobs.getPk_id() == null){
            tickedJobsSrv.setPk_id(tickedJobsDao.getNewPkId());
        }
        
        tickedJobsDao.save(new TickedJobsDaoPojo(tickedJobsSrv));
        
        return tickedJobsSrv;
    }

    @Override
    @Transactional
    public void delete(TickedJobs tickedJobs) {
        tickedJobsDao.delete(new TickedJobsDaoPojo(tickedJobs));
    }
    
    @Override
    @Transactional
    public TickedJobsLstCtrlPojo allNotProcessedTickedJobs(){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        TickedJobsLstCtrlPojo tickedJobsFiltered = new TickedJobsLstCtrlPojo();
        List<? extends TickedJobs> tickedJobsAll = tickedJobsDao.selectAllTickedJobs();
        try { 
            List<TimeRecording> timeRecordings = sessionTimesheet.getTimesheetTable();

            for (TimeRecording timeRecording: timeRecordings){
                boolean found = false;
                for (TickedJobs tickedJob: tickedJobsAll){
                    if (timeRecording.getTicked().equals(format.format(tickedJob.getTicked()))){
                        found = true;
                    }
                }
                
                if (!found){
                    TickedJobsCtrlPojo tickedJobsCtrlPojo = new TickedJobsCtrlPojo();
                    
                    tickedJobsCtrlPojo.setSqlite_id(timeRecording.getSqlite_id());
                    
                    if (timeRecording.getStarted().equals(10)){
                        tickedJobsCtrlPojo.setStarted(true);
                    } else {
                        tickedJobsCtrlPojo.setStarted(false);
                    }
                    
                    tickedJobsCtrlPojo.setTicked(format.parse(timeRecording.getTicked()));
                    
                    Calendar cal = Calendar.getInstance(); // locale-specific
                    cal.setTime(tickedJobsCtrlPojo.getTicked());
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.MILLISECOND, 0);
                    
                    List<JobsCtrlPojo> allJobs = new ArrayList<JobsCtrlPojo>();
                    List<? extends Jobs> selectedJobs = jobsDao.selectDate(cal.getTime());
  
                    for(Jobs job: selectedJobs){
                        allJobs.add(new JobsCtrlPojo(job));
                    }
                    
                    if (selectedJobs.size() == 1){
                        tickedJobsCtrlPojo.setSelectedJobId(selectedJobs.get(0).getPk_id());
                    }
                    
                    tickedJobsCtrlPojo.setJobs(allJobs);
                    
                    tickedJobsFiltered.add(tickedJobsCtrlPojo);
                }
            }
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
        return tickedJobsFiltered;
    }

    @Override
    @Transactional
    public void processTickedJobs(List<TickedJobsCtrlPojo> tickedJobs) {
        for(TickedJobsCtrlPojo tickedJob: tickedJobs){
            if (tickedJob.getSelectedJobId() != null){
                TickedJobsSrvPojo tickedJobsSrvPojo = new TickedJobsSrvPojo();

                tickedJobsSrvPojo.setPk_id(tickedJob.getPk_id());
                tickedJobsSrvPojo.setSqlite_id(tickedJob.getSqlite_id());
                tickedJobsSrvPojo.setStarted(tickedJob.isStarted());
                tickedJobsSrvPojo.setTicked(tickedJob.getTicked());

                for(Jobs job: tickedJob.getJobs()){
                    if (tickedJob.getSelectedJobId().equals(job.getPk_id())){
                        tickedJobsSrvPojo.setJob(new JobsSrvPojo(job));
                    }
                }

                if (tickedJobsSrvPojo.getPk_id() == null){
                    tickedJobsSrvPojo.setPk_id(tickedJobsDao.getNewPkId());
                }

                tickedJobsDao.save(new TickedJobsDaoPojo(tickedJobsSrvPojo));
            }
        }
    }

    @Override
    @Transactional
    public List<? extends TickedJobs> selectTickedJobsByJob(Jobs job) {
        return tickedJobsDao.selectTickedJobsByJob(job.getPk_id());
    }
}
