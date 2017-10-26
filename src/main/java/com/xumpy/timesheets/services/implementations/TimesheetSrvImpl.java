/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services.implementations;

import com.itextpdf.text.DocumentException;
import com.xumpy.itext.services.TimeSheet;
import com.xumpy.thuisadmin.dao.sqlite.model.TimeRecording;
import com.xumpy.timesheets.dao.implementations.JobsDaoImpl;
import com.xumpy.timesheets.dao.implementations.JobsGroupDaoImpl;
import com.xumpy.timesheets.domain.Jobs;
import com.xumpy.timesheets.domain.JobsGroup;
import com.xumpy.timesheets.services.TimesheetSrv;
import com.xumpy.timesheets.services.rest.RestTimesheet;
import com.xumpy.timesheets.services.rest.RestTimesheetDetail;
import com.xumpy.timesheets.services.rest.RestTimesheetHour;
import com.xumpy.timesheets.services.rest.TimesheetWebService;
import com.xumpy.utilities.CustomDateUtils;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nico
 */
@Service
public class TimesheetSrvImpl implements TimesheetSrv{

    private Logger log = Logger.getLogger(TimesheetSrvImpl.class.getName());
    private DateFormat format = new SimpleDateFormat("ddMMyyyy", Locale.ENGLISH);
    private static final int MONTHS_TO_GET_FROM_WEBSERVICE = 2;

    @Autowired JobsDaoImpl jobsDao;
    @Autowired JobsGroupDaoImpl jobsGroupDao;
    @Autowired TimesheetWebService timesheetWebService;
    @Override
    @Transactional
    public OutputStream getTimesheet(Integer jobsGroupId, String month, OutputStream outputStream){
        try {
            log.info("JobsGroupId: " + jobsGroupId);
            log.info("Month: " + month);
            
            List<? extends Jobs> jobs = jobsDao.selectPeriode(CustomDateUtils.getFirstDayOfMonth(month), CustomDateUtils.getLastDayOfMonth(month));
            List<Jobs> jobsInGroup = new ArrayList<Jobs>();
            
            log.info("Get Jobs total: " + jobs.size());
            
            JobsGroup jobsGroup = jobsGroupDao.findOne(jobsGroupId);
            
            for (Jobs job: jobs){
                if (job.getJobsGroup().getPk_id().equals(jobsGroup.getPk_id())){
                    jobsInGroup.add(job);
                }
            }
            
            log.info("Get jobsInGroup total: " + jobsInGroup.size());
            
            TimeSheet timeSheet = new TimeSheet();
            
            return timeSheet.pdf(jobsInGroup, outputStream);
        } catch (ParseException ex) {
            Logger.getLogger(TimesheetSrvImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(TimesheetSrvImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TimesheetSrvImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(TimesheetSrvImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    private String getStartDate(){
        Date referenceDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(referenceDate);
        c.add(Calendar.MONTH, -MONTHS_TO_GET_FROM_WEBSERVICE);
        return format.format(c.getTime());
    }

    private String getEndDate(){
        return format.format(new Date());
    }

    private TimeRecording createTimeRecording(Integer seqId, String hoursBatch, Integer ticked){
        TimeRecording timeRecording = new TimeRecording();

        timeRecording.setSqlite_id(seqId);
        timeRecording.setStarted(ticked);
        timeRecording.setTicked(hoursBatch);

        return timeRecording;
    }

    private String createFullDate(String dateIn, String hoursIn){
        return dateIn + " " + hoursIn;
    }

    private List<TimeRecording> transformRestTimesheetToTimeRecording(RestTimesheet restTimesheet){
        List<TimeRecording> timeRecordings = new LinkedList<TimeRecording>();

        for (RestTimesheetDetail restTimesheetDetail: restTimesheet.getBatches()){
            if (restTimesheetDetail.getHoursBatchIn() != null){
                for(RestTimesheetHour hoursBatchIn: restTimesheetDetail.getHoursBatchIn()){
                    timeRecordings.add(createTimeRecording(hoursBatchIn.getSeqNr(), createFullDate(restTimesheetDetail.getDate(), hoursBatchIn.getHours()), 10));
                }
            }
            if (restTimesheetDetail.getHoursBatchOut() != null){
                for(RestTimesheetHour hoursBatchOut: restTimesheetDetail.getHoursBatchOut()){
                    timeRecordings.add(createTimeRecording(hoursBatchOut.getSeqNr(), createFullDate(restTimesheetDetail.getDate(), hoursBatchOut.getHours()), 20));
                }
            }
        }

        return timeRecordings;
    }

    public List<TimeRecording> getTimeRecordingFromWeb(String ip){
        return transformRestTimesheetToTimeRecording(timesheetWebService.callWebService(ip, getStartDate(), getEndDate()));
    }
}
