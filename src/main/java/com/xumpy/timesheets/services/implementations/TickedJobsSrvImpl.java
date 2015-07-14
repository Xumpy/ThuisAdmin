/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services.implementations;

import com.xumpy.thuisadmin.dao.sqlite.LoadSqlLite;
import com.xumpy.thuisadmin.dao.sqlite.model.TimeRecording;
import com.xumpy.timesheets.dao.implementations.JobsDaoImpl;
import com.xumpy.timesheets.dao.implementations.TickedJobsDaoImpl;
import com.xumpy.timesheets.domain.Jobs;
import com.xumpy.timesheets.domain.TickedJobs;
import com.xumpy.timesheets.services.TickedJobsSrv;
import com.xumpy.timesheets.services.model.JobsSrvPojo;
import com.xumpy.timesheets.services.model.TickedJobsSrvPojo;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    @Override
    @Transactional
    public TickedJobs select(Integer pk_id) {
        return tickedJobsDao.select(pk_id);
    }

    @Override
    @Transactional
    public List<TickedJobs> selectAllTickedJobs() {
        return tickedJobsDao.selectAllTickedJobs();
    }

    @Override
    @Transactional
    public TickedJobs save(TickedJobs tickedJobs) {
        TickedJobsSrvPojo tickedJobsSrv = new TickedJobsSrvPojo(tickedJobs);
        if (tickedJobs.getPk_id() == null){
            tickedJobsSrv.setPk_id(tickedJobsDao.getNewPkId());
        }
        
        tickedJobsDao.save(tickedJobsSrv);
        
        return tickedJobsSrv;
    }

    @Override
    @Transactional
    public void delete(TickedJobs tickedJobs) {
        tickedJobsDao.delete(tickedJobs);
    }
    
    @Override
    @Transactional
    public List<TickedJobsSrvPojo> allNotProcessedTickedJobs(){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        List<TickedJobsSrvPojo> tickedJobsFiltered = new ArrayList<TickedJobsSrvPojo>();
        List<TickedJobs> tickedJobsAll = tickedJobsDao.selectAllTickedJobs();
        try { 
            List<TimeRecording> timeRecordings = LoadSqlLite.loadTimeRecordings();
            
            for (TimeRecording timeRecording: timeRecordings){
                boolean found = false;
                for (TickedJobs tickedJob: tickedJobsAll){
                    if (timeRecording.getSqlite_id().equals(tickedJob.getSqlite_id())){
                        found = true;
                    }
                }
                
                if (!found){
                    TickedJobsSrvPojo tickedJobsSrvPojo = new TickedJobsSrvPojo();
                    
                    tickedJobsSrvPojo.setSqlite_id(timeRecording.getSqlite_id());
                    
                    if (timeRecording.getStarted().equals(10)){
                        tickedJobsSrvPojo.setStarted(true);
                    } else {
                        tickedJobsSrvPojo.setStarted(false);
                    }
                    
                    tickedJobsSrvPojo.setTicked(format.parse(timeRecording.getTicked()));
                    
                    Calendar cal = Calendar.getInstance(); // locale-specific
                    cal.setTime(tickedJobsSrvPojo.getTicked());
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.MILLISECOND, 0);
                    
                    List<Jobs> allJobsOnDate = jobsDao.selectDate(cal.getTime());
                    
                    if (allJobsOnDate.size() == 1){
                        tickedJobsSrvPojo.setJob(new JobsSrvPojo(allJobsOnDate.get(0)));
                    }
                    
                    tickedJobsFiltered.add(tickedJobsSrvPojo);
                }
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TickedJobsSrvImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TickedJobsSrvImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(TickedJobsSrvImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return tickedJobsFiltered;
    }
}
