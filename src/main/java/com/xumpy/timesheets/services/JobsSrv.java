/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services;

import com.xumpy.timesheets.domain.Jobs;
import com.xumpy.timesheets.domain.JobsGroup;
import com.xumpy.timesheets.services.model.JobsInJobsGroup;
import com.xumpy.timesheets.services.model.JobsSrvPojo;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author nicom
 */
public interface JobsSrv {
    Jobs select(Integer pk_id);
    List<? extends Jobs> selectDate(Date date);
    List<? extends Jobs> selectMonth(String month) throws ParseException;
    List<? extends Jobs> selectPeriode(Date startDate, Date endDate);
    public List<JobsInJobsGroup> selectMonthJobsInJobGroup(String month) throws ParseException;
    public List<JobsInJobsGroup> selectPeriodeJobsInJobGroup(Date startDate, Date endDate);
    List<? extends Jobs> saveJobs(List<? extends Jobs> jobs);
    Jobs save(Jobs jobs);
    void delete(Jobs jobs); 
    List<? extends Jobs> fillMonth(List<? extends Jobs> jobs) throws ParseException;
    List<? extends Jobs> filterMonth(List<? extends Jobs> jobs);
}
