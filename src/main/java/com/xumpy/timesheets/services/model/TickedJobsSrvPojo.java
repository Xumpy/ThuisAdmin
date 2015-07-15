/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services.model;

import com.xumpy.timesheets.domain.Jobs;
import com.xumpy.timesheets.domain.TickedJobs;
import java.util.Date;

/**
 *
 * @author nicom
 */
public class TickedJobsSrvPojo implements TickedJobs{
    
    private Integer pk_id;
    private JobsSrvPojo job;
    private Integer sqlite_id;
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
    public boolean isStarted() {
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
        this.started = tickedJobs.isStarted();
        this.ticked = tickedJobs.getTicked();
    }
}
