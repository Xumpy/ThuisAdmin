/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services.implementations;

import com.xumpy.timesheets.controller.model.JobsCtrlPojo;
import com.xumpy.timesheets.controller.model.Overview;
import com.xumpy.timesheets.dao.implementations.JobsDaoImpl;
import com.xumpy.timesheets.domain.Jobs;
import com.xumpy.timesheets.domain.JobsGroup;
import com.xumpy.timesheets.services.JobsGroupSrv;
import com.xumpy.timesheets.services.JobsSrv;
import com.xumpy.timesheets.services.model.JobsGroupSrvPojo;
import com.xumpy.timesheets.controller.model.JobsInJobsGroup;
import com.xumpy.timesheets.dao.model.JobsDaoPojo;
import com.xumpy.timesheets.services.model.JobsSrvPojo;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nicom
 */
@Service
public class JobsSrvImpl implements JobsSrv{

    @Autowired JobsDaoImpl jobsDao;
    @Autowired JobsGroupSrv jobsGroupSrv;
    
    @Override
    @Transactional
    public Jobs select(Integer pk_id) {
        return jobsDao.findById(pk_id).get();
    }

    @Override
    @Transactional
    public List<? extends Jobs> selectDate(Date date) {
        return jobsDao.selectDate(date);
    }

    @Override
    @Transactional
    public List<? extends Jobs> selectPeriode(Date startDate, Date endDate) {
        return jobsDao.selectPeriode(startDate, endDate);
    }

    public Jobs saveInTransaction(Jobs jobs){
        JobsSrvPojo jobsSrvPojo = new JobsSrvPojo(jobs);
        
        if (jobsSrvPojo.getPk_id() == null){
            jobsSrvPojo.setPk_id(jobsDao.getNewPkId());
        }
        
        if (!jobs.getWorkedHours().equals(new BigDecimal(0))){
            jobsDao.save(new JobsDaoPojo(jobsSrvPojo));
        } else {
            jobsDao.delete(new JobsDaoPojo(jobs));
        }
        
        return jobsSrvPojo;    
    }
    
    @Override
    @Transactional
    public Jobs save(Jobs jobs) {
        return saveInTransaction(jobs);
    }

    @Override
    @Transactional
    public void delete(Jobs jobs) {
        jobsDao.delete(new JobsDaoPojo(jobs));
    }

    @Override
    @Transactional
    public List<? extends Jobs> selectMonth(String month) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = df.parse("01/" + month);
        
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endDate = c.getTime();
        
        return jobsDao.selectPeriode(startDate, endDate);
    }

    @Override
    @Transactional
    public List<? extends Jobs> saveJobs(List<? extends Jobs> jobs) {
        List<JobsSrvPojo> lstJobSrvPojo = new ArrayList<JobsSrvPojo>();
        
        for(int i=0; i<jobs.size(); i++){
            JobsSrvPojo jobsSrvPojo = new JobsSrvPojo(jobs.get(i));
            jobsSrvPojo = new JobsSrvPojo(saveInTransaction(jobsSrvPojo));
            
            lstJobSrvPojo.add(jobsSrvPojo);
        }
        
        return lstJobSrvPojo;
    }

    @Override
    @Transactional
    public List<JobsInJobsGroup> selectPeriodeJobsInJobGroup(Date startDate, Date endDate) {
        List<? extends Jobs> allJobsInPeriode = jobsDao.selectPeriode(startDate, endDate);
        List<JobsInJobsGroup> jobsInAllJobGroup = new ArrayList<JobsInJobsGroup>();
        
        for(Jobs job: allJobsInPeriode){
            jobsInAllJobGroup = putJobInJobsGroup(jobsInAllJobGroup, new JobsSrvPojo(job));
        }
        
        return jobsInAllJobGroup;
    }

    public List<JobsInJobsGroup> putJobInJobsGroup(List<JobsInJobsGroup> jobsInAllJobGroup, JobsSrvPojo job){
        boolean jobFound = false;
        
        for (JobsInJobsGroup jobsInJobsGroup: jobsInAllJobGroup){
            if (jobsInJobsGroup.getPk_id().equals(job.getJobsGroup().getPk_id())){
                boolean jobDateFound = false;
                for (Jobs jobs: jobsInJobsGroup.getJobs()){
                    if (jobs.getJobDate().equals(job.getJobDate())){
                        jobDateFound = true;
                    }
                }
                
                if (!jobDateFound){
                    jobsInJobsGroup.getJobs().add(new JobsCtrlPojo(job));
                    jobFound = true;
                }
            }
        }
        
        if (!jobFound){
            JobsInJobsGroup jobsInJobsGroup = new JobsInJobsGroup();
            
            jobsInJobsGroup.setPk_id(job.getJobsGroup().getPk_id());
            jobsInJobsGroup.setName(job.getJobsGroup().getName());
            jobsInJobsGroup.setDescription(job.getJobsGroup().getDescription());
            
            List<JobsCtrlPojo> jobs = new ArrayList<JobsCtrlPojo>();
            jobs.add(new JobsCtrlPojo(job));
            jobsInJobsGroup.setJobs(jobs);
            
            jobsInAllJobGroup.add(jobsInJobsGroup);
        }
        
        return jobsInAllJobGroup;
    }
    
    public static List<? extends Jobs> addZeroDates(List<? extends Jobs> jobs, String month, JobsGroup jobsGroup) throws ParseException{
        SimpleDateFormat dfOnlyDay = new SimpleDateFormat("dd");
        List<Jobs> allJobsInMonth = new ArrayList<Jobs>();
        
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = df.parse("01/" + month);
        
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(startDate);
        endDate.set(Calendar.DAY_OF_MONTH, endDate.getActualMaximum(Calendar.DAY_OF_MONTH));
        endDate.add(Calendar.DATE, 1);
        
        Calendar iteratorDate = Calendar.getInstance();
        iteratorDate.setTime(startDate);
        
        while(iteratorDate.before(endDate)){
            boolean found = false;
            JobsSrvPojo jobsSrvPojo = new JobsSrvPojo();
                
            for (Jobs job: jobs){
                if (df.format(job.getJobDate()).equals(df.format(iteratorDate.getTime()))){
                    found = true;
                    jobsSrvPojo = new JobsSrvPojo(job);
                }
            }
            
            if (!found){
                jobsSrvPojo.setJobDate(iteratorDate.getTime());
                jobsSrvPojo.setJobsGroup(new JobsGroupSrvPojo(jobsGroup));
                jobsSrvPojo.setWorkedHours("0");
                jobsSrvPojo.setWeekendDay(jobInWeekend(jobsSrvPojo));
            }
            jobsSrvPojo.setJobDay(dfOnlyDay.format(jobsSrvPojo.getJobDate()));
            allJobsInMonth.add(jobsSrvPojo);
            iteratorDate.add(Calendar.DATE, 1);
        }
        
        return allJobsInMonth;
    }
    
    @Override
    public List<? extends Jobs> fillMonth(List<? extends Jobs> jobs) throws ParseException{
        SimpleDateFormat dfMonth = new SimpleDateFormat("MM/yyyy");
        String month = dfMonth.format(jobs.get(0).getJobDate());
        
        return addZeroDates(jobs, month, jobs.get(0).getJobsGroup());
    }

    @Override
    public List<Jobs> filterMonth(List<? extends Jobs> jobs) {
        List<Jobs> filteredJobs = new ArrayList<Jobs>();
        
        for(Jobs job: jobs){
            if (job.getWorkedHours() != null && !job.getWorkedHours().equals(new BigDecimal(0)) || job.getPk_id() != null){
                filteredJobs.add(job);
            }
        }
        
        return filteredJobs;
    }

    @Override
    @Transactional
    public List<JobsInJobsGroup> selectMonthJobsInJobGroup(String month, Overview overview) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = df.parse("01/" + month);

        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endDate = c.getTime();
        
        List<JobsInJobsGroup> periodeJobsInJobsGroup = selectPeriodeJobsInJobGroup(startDate, endDate);
        
        for(int i=0; i<periodeJobsInJobsGroup.size();i++){
            JobsInJobsGroup jobsInJobsGroup = periodeJobsInJobsGroup.get(i);
            
            List<JobsCtrlPojo> jobsCtrlPojo = new ArrayList<JobsCtrlPojo>();
            for (Jobs job: fillMonth(jobsInJobsGroup.getJobs())){
                jobsCtrlPojo.add(new JobsCtrlPojo(job));
            }
            
            jobsInJobsGroup.setJobs(jobsCtrlPojo);
            periodeJobsInJobsGroup.set(i, jobsInJobsGroup);
        }
        
        overview.setMonth(month);
        overview.setAllJobsInJobsGroup(periodeJobsInJobsGroup);
        
        return periodeJobsInJobsGroup;
    }
    
    public static boolean jobInWeekend(Jobs jobs){
        Date date = jobs.getJobDate();
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ){
            return true;
        }
        
        return false;
    }
}
