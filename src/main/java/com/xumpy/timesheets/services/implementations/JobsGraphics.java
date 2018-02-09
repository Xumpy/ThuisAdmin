/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services.implementations;

import com.xumpy.timesheets.dao.implementations.JobsDaoImpl;
import com.xumpy.timesheets.domain.Jobs;
import com.xumpy.timesheets.domain.JobsGroup;
import com.xumpy.timesheets.domain.OverviewWork.OverviewWork;
import com.xumpy.timesheets.domain.OverviewWork.OverviewWorkDetails;
import com.xumpy.timesheets.services.model.WorkingDay;
import com.xumpy.timesheets.services.model.OverviewWorkSrvPojo.MonthlyOverviewWorkDetailsSrvPojo;
import com.xumpy.timesheets.services.model.OverviewWorkSrvPojo.OverviewWorkDetailsSrvPojo;
import com.xumpy.timesheets.services.model.OverviewWorkSrvPojo.OverviewWorkSrvPojo;
import com.xumpy.utilities.CustomDateUtils;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JobsGraphics {
    
    @Autowired JobsDaoImpl jobsDao;
    
    private final SimpleDateFormat dfToMonth = new SimpleDateFormat("MM/yyyy");
    
    public WorkingDay getWorkingDay(List<WorkingDay> workingDays, Date date){
        for(WorkingDay workingDay: workingDays){
            if (workingDay.getDate().equals(date)){
                return workingDay;
            }
        }
        return null;
    }
    
    public List<WorkingDay> calculateWorkingDates(List<Jobs> jobs){
        List<WorkingDay> workingDays = new ArrayList<WorkingDay>();
        
        List<BigDecimal> lstPayedHours = new ArrayList<BigDecimal>();
        
        for(Jobs job: jobs){
            if (getWorkingDay(workingDays, job.getJobDate()) == null){
                WorkingDay workingDay = new WorkingDay();
                workingDay.setDate(job.getJobDate());
                workingDay.setJobs(new ArrayList<Jobs>());
                workingDay.setActualWorkHours(new BigDecimal(0));
                workingDay.setOvertimeHours(new BigDecimal(0));
                workingDay.setOvertimeHoursPayed(new BigDecimal(0));
                
                workingDays.add(workingDay);
            }
            
            if (job.getJobsGroup().getCompany() != null){
                if (!lstPayedHours.contains(job.getJobsGroup().getCompany().getDailyPayedHours())){
                    lstPayedHours.add(job.getJobsGroup().getCompany().getDailyPayedHours());
                }
            }
        }
        
        BigDecimal payedHours = new BigDecimal(0);
        
        for (BigDecimal payedHoursFromList: lstPayedHours){
            if (payedHoursFromList.compareTo(payedHours) > 0){
                payedHours = payedHoursFromList;
            }
        }

        for (WorkingDay workingDay: workingDays){
            for (Jobs job: jobs){
                if (job.getJobDate().equals(workingDay.getDate())){
                    workingDay.getJobs().add(job);
                    if (job.getPercentage() == null || job.getPercentage().equals(new BigDecimal(0))){
                        workingDay.setActualWorkHours(workingDay.getActualWorkHours().add(job.getWorkedHours()));
                    } else {
                        workingDay.setActualWorkHours(workingDay.getActualWorkHours().add(job.getWorkedHours().multiply(job.getPercentage().divide(new BigDecimal(100)))));
                    }
                    workingDay.setOvertimeHours(workingDay.getActualWorkHours().subtract(payedHours));
                }
            }
        }

        return workingDays;
    }

    public OverviewWorkDetails getOverviewWorkDetails(List<Jobs> jobs) throws ParseException{
        MathContext mc = new MathContext(3, RoundingMode.HALF_UP);
        
        OverviewWorkDetailsSrvPojo overviewWorkDetails = new OverviewWorkDetailsSrvPojo();
        
        List<WorkingDay> workingDays = calculateWorkingDates(jobs);
        
        overviewWorkDetails.setHoursToWorkPerDay(new BigDecimal(8)); // Hardcoded
        
        BigDecimal hoursPayedPerDay = new BigDecimal(0, mc);
        BigDecimal workedWeekHours = new BigDecimal(0, mc);
        BigDecimal workedWeekendHours = new BigDecimal(0, mc);
        BigDecimal weekDays = new BigDecimal(0, mc);
        BigDecimal weekendDays = new BigDecimal(0, mc);
        BigDecimal workedWeekDays = new BigDecimal(0, mc);
        BigDecimal workedWeekendDays = new BigDecimal(0, mc);
        BigDecimal overtimeHours = new BigDecimal(0, mc);
        BigDecimal overtimeDays = new BigDecimal(0, mc);
        
        for(WorkingDay workingDay: workingDays){
            if (CustomDateUtils.isWeekDay(workingDay.getDate())){
                weekDays = weekDays.add(new BigDecimal(1));
                workedWeekHours = workedWeekHours.add(workingDay.getActualWorkHours(), mc);
                workedWeekDays = workedWeekDays.add(new BigDecimal(1));
            } else {
                weekendDays = weekendDays.add(new BigDecimal(1));
                workedWeekendHours = workedWeekendHours.add(workingDay.getActualWorkHours(), mc);
                workedWeekendDays = workedWeekendDays.add(new BigDecimal(1));
            }
            
            overtimeHours = overtimeHours.add(workingDay.getOvertimeHours(), mc);

            for(Jobs jobPerDay: workingDay.getJobs()){
                if(jobPerDay.getJobsGroup().getCompany() != null){
                    if (hoursPayedPerDay.compareTo(jobPerDay.getJobsGroup().getCompany().getDailyPayedHours()) < 0){
                        hoursPayedPerDay = jobPerDay.getJobsGroup().getCompany().getDailyPayedHours();
                    }
                }
            }
            
            if (!hoursPayedPerDay.equals(new BigDecimal(0))){        
                overtimeDays = overtimeHours.divide(hoursPayedPerDay, mc);
            } else {
                overtimeDays = workedWeekDays.add(workedWeekendDays);
            }
        }
        
        overviewWorkDetails.setHoursPayedPerDay(hoursPayedPerDay);
        
        overviewWorkDetails.setWorkedWeekHours(workedWeekHours);
        overviewWorkDetails.setWorkedWeekendHours(workedWeekendHours);
        overviewWorkDetails.setWeekDays(weekDays);
        overviewWorkDetails.setWeekendDays(weekendDays);
        
        overviewWorkDetails.setWorkedWeekDays(workedWeekDays);
        overviewWorkDetails.setWorkedWeekendDays(workedWeekendDays);
        
        overviewWorkDetails.setOvertimeHours(overtimeHours);
        overviewWorkDetails.setOvertimeDays(overtimeDays);
        
        return overviewWorkDetails;
    }
    
    public List<Jobs> getJobsInMonth(List<? extends Jobs> jobs, String Month) throws ParseException{
        List<Jobs> filteredJobs = new ArrayList<Jobs>();
        
        for(Jobs job: jobs){
            if (dfToMonth.format(job.getJobDate()).equals(Month)){
                filteredJobs.add(job);
            }
        }
        
        return filteredJobs;
    }
    
    public OverviewWork overviewWork(List<Jobs> jobs, Date firstDate, Date lastDate) throws ParseException{
        OverviewWorkSrvPojo overviewWork = new OverviewWorkSrvPojo();
        
        List<MonthlyOverviewWorkDetailsSrvPojo> lstMonthlyOverviewWorkDetails= new ArrayList<MonthlyOverviewWorkDetailsSrvPojo>();
        
        List<String> months = CustomDateUtils.getAllMonthsBetweenMonths(dfToMonth.format(firstDate), dfToMonth.format(lastDate));
        
        for (String month: months){
            MonthlyOverviewWorkDetailsSrvPojo monthlyOverviewWorkDetails = new MonthlyOverviewWorkDetailsSrvPojo();
            List<Jobs> monthlyJobs = getJobsInMonth(jobs, month);
            
            monthlyOverviewWorkDetails.setMonth(month);
            monthlyOverviewWorkDetails.setWorkDetails(new OverviewWorkDetailsSrvPojo(getOverviewWorkDetails(monthlyJobs)));
            
            lstMonthlyOverviewWorkDetails.add(monthlyOverviewWorkDetails);
        }
        
        overviewWork.setWorkDetails(getOverviewWorkDetails(jobs));
        overviewWork.setMonthlyWorkDetails(lstMonthlyOverviewWorkDetails);
        
        return overviewWork;
    }
    
    @Transactional
    public OverviewWork overviewWork(String beginMonth, String endMonth, List<? extends JobsGroup> lstJobsGroup) throws ParseException{
        Date firstDay = CustomDateUtils.getFirstDayOfMonth(beginMonth);
        Date lastDay = CustomDateUtils.getLastDayOfMonth(endMonth);
        
        List<? extends Jobs> jobs = jobsDao.selectPeriode(firstDay, lastDay);
        List<Jobs> filteredJobs = new ArrayList<Jobs>();
        
        for(Jobs job: jobs){
            for(JobsGroup jobsGroup: lstJobsGroup){
                if (job.getJobsGroup().getPk_id().equals(jobsGroup.getPk_id())){
                    filteredJobs.add(job);
                }
            }
        }
        
        return overviewWork(filteredJobs, firstDay, lastDay);
    }
}
