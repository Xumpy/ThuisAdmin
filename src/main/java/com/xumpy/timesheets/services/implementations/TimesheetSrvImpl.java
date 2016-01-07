/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services.implementations;

import com.itextpdf.text.DocumentException;
import com.xumpy.itext.services.TimeSheet;
import com.xumpy.timesheets.dao.JobsDao;
import com.xumpy.timesheets.dao.JobsGroupDao;
import com.xumpy.timesheets.domain.Jobs;
import com.xumpy.timesheets.domain.JobsGroup;
import com.xumpy.timesheets.services.TimesheetSrv;
import com.xumpy.utilities.CustomDateUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
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
    
    @Autowired JobsDao jobsDao;
    @Autowired JobsGroupDao jobsGroupDao;
    
    @Override
    @Transactional(value="transactionManager")
    public OutputStream getTimesheet(Integer jobsGroupId, String month, OutputStream outputStream){
        try {
            log.info("JobsGroupId: " + jobsGroupId);
            log.info("Month: " + month);
            
            List<Jobs> jobs = jobsDao.selectPeriode(CustomDateUtils.getFirstDayOfMonth(month), CustomDateUtils.getLastDayOfMonth(month));
            List<Jobs> jobsInGroup = new ArrayList<Jobs>();
            
            log.info("Get Jobs total: " + jobs.size());
            
            JobsGroup jobsGroup = jobsGroupDao.select(jobsGroupId);
            
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
    
}
