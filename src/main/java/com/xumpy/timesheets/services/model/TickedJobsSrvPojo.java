/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xumpy.timesheets.domain.Jobs;
import com.xumpy.timesheets.domain.TickedJobs;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author nicom
 */
public class TickedJobsSrvPojo implements TickedJobs{
    
    private Integer pk_id;
    private JobsSrvPojo job;
    private Integer sqlite_id;
    
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="CET")
    private Date ticked;
    private boolean started;
    
    @Override
    public Integer getPk_id() {
        return pk_id;
    }

    @Override
    public Jobs getJob() {
        return job;
    }

    @Override
    public Integer getSqlite_id() {
        return sqlite_id;
    }

    @Override
    public Date getTicked() {
        return ticked;
    }

    @Override
    public boolean getStarted() {
        return started;
    }

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    public void setJob(JobsSrvPojo job) {
        this.job = job;
    }

    public void setSqlite_id(Integer sqlite_id) {
        this.sqlite_id = sqlite_id;
    }

    public void setTicked(Date ticked) {
        this.ticked = ticked;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
    
    public TickedJobsSrvPojo(){ }
    
    public TickedJobsSrvPojo(TickedJobs tickedJobs){
        this.job = new JobsSrvPojo(tickedJobs.getJob());
        this.pk_id = tickedJobs.getPk_id();
        this.sqlite_id = tickedJobs.getSqlite_id();
        this.started = tickedJobs.getStarted();
        this.ticked = tickedJobs.getTicked();
    }
}
