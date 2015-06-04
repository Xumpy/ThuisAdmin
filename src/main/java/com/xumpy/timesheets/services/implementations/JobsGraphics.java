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
    
    public BigDecimal getWorkedWeekHours(List<Jobs> jobs){
        BigDecimal workedWeekHours = new BigDecimal(0);
        for (Jobs job: jobs){
            if (CustomDateUtils.isWeekDay(job.getJobDate())){
                workedWeekHours = workedWeekHours.add(job.getWorkedHours());
            }
        }
        return workedWeekHours;
    }
    
    public BigDecimal getWorkedWeekDays(List<Jobs> jobs){
        BigDecimal workedWeekDays = new BigDecimal(0);
        List<Date> lstDates = new ArrayList<Date>();
        
        for (Jobs job: jobs){
            if (CustomDateUtils.isWeekDay(job.getJobDate())){
                if (!lstDates.contains(job.getJobDate())){
                    lstDates.add(job.getJobDate());
                    workedWeekDays = workedWeekDays.add(new BigDecimal(1));
                }
            }
        }
        return workedWeekDays;
    }
    
    public BigDecimal getWorkedWeekendHours(List<Jobs> jobs){
        BigDecimal workedWeekendHours = new BigDecimal(0);
        for (Jobs job: jobs){
            if (!CustomDateUtils.isWeekDay(job.getJobDate())){
                workedWeekendHours = workedWeekendHours.add(job.getWorkedHours());
            }
        }
        return workedWeekendHours;
    }
    
    public BigDecimal getWorkedWeekendDays(List<Jobs> jobs){
        BigDecimal workedWeekendDays = new BigDecimal(0);
        List<Date> lstDate = new ArrayList<Date>();
        
        for (Jobs job: jobs){
            if (CustomDateUtils.isWeekDay(job.getJobDate())){
                if (!lstDate.contains(job.getJobDate())){
                    lstDate.add(job.getJobDate());
                    workedWeekendDays = workedWeekendDays.add(new BigDecimal(1));
                }
            }
        }
        return workedWeekendDays;
    }
    
    public BigDecimal getWeekDays(List<Jobs> jobs) throws ParseException{
        List<String> months = new ArrayList<String>();
        
        BigDecimal weekDays = new BigDecimal(0);
        
        for(Jobs job: jobs){
            if(!months.contains(dfToMonth.format(job.getJobDate()))){
                months.add(dfToMonth.format(job.getJobDate()));
            }
        }
        
        for (String month: months){
            weekDays.add(new BigDecimal(CustomDateUtils.getWeekDays(month).size()));
        }
        
        return weekDays;
    }
    
    public BigDecimal getWeekendDays(List<Jobs> jobs) throws ParseException{
        List<String> months = new ArrayList<String>();
        
        BigDecimal weekendDays = new BigDecimal(0);
        
        for(Jobs job: jobs){
            if(!months.contains(dfToMonth.format(job.getJobDate()))){
                months.add(dfToMonth.format(job.getJobDate()));
            }
        }
        
        for (String month: months){
            weekendDays.add(new BigDecimal(CustomDateUtils.getWeekendDays(month).size()));
        }
        
        return weekendDays;
    }
    
    public OverviewWorkDetails getOverviewWorkDetails(List<Jobs> jobs) throws ParseException{
        MathContext mc = new MathContext(3, RoundingMode.HALF_UP);
        
        OverviewWorkDetailsSrvPojo overviewWorkDetails = new OverviewWorkDetailsSrvPojo();
        
        overviewWorkDetails.setHoursToWorkPerDay(new BigDecimal(8)); // Hardcoded
        overviewWorkDetails.setHoursPayedPerDay(new BigDecimal(7.6)); // Hardcoded
        
        overviewWorkDetails.setWorkedWeekHours(getWorkedWeekHours(jobs));
        overviewWorkDetails.setWorkedWeekendHours(getWorkedWeekendHours(jobs));
        overviewWorkDetails.setWeekDays(getWeekDays(jobs));
        overviewWorkDetails.setWeekendDays(getWeekendDays(jobs));
        
        overviewWorkDetails.setWorkedWeekDays(getWorkedWeekDays(jobs));
        overviewWorkDetails.setWorkedWeekendDays(getWorkedWeekendDays(jobs));
        
        overviewWorkDetails.setOvertimeHours(overviewWorkDetails.getWorkedWeekHours()
                                                .subtract(overviewWorkDetails.getHoursPayedPerDay()
                                                            .multiply(overviewWorkDetails.getWorkedWeekDays(), mc), mc)
                                                .add(overviewWorkDetails.getWorkedWeekendHours(), mc));
        
        overviewWorkDetails.setOvertimeDays(overviewWorkDetails.getOvertimeHours().divide(overviewWorkDetails.getHoursPayedPerDay(), mc));
        
        return overviewWorkDetails;
    }
    
    public List<Jobs> getJobsInMonth(List<Jobs> jobs, String Month) throws ParseException{
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
        
        List<Jobs> jobs = jobsDao.selectPeriode(firstDay, lastDay);
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
