/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.controller.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.xumpy.timesheets.domain.Jobs;
import com.xumpy.timesheets.domain.TickedJobs;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author nicom
 */
public class TickedJobsCtrlPojo implements TickedJobs, Serializable{
    private Integer pk_id;
    private JobsCtrlPojo job;
    private List<JobsCtrlPojo> jobs;
    private Integer selectedJobId;
    private Integer sqlite_id;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="CET") private Date ticked;
    private boolean started;

    @Override
    public Integer getPk_id() {
        return pk_id;
    }

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    public List<JobsCtrlPojo> getJobs() {
        return jobs;
    }

    public void setJobs(List<JobsCtrlPojo> job) {
        this.jobs = job;
    }

    @Override
    public Integer getSqlite_id() {
        return sqlite_id;
    }

    public void setSqlite_id(Integer sqlite_id) {
        this.sqlite_id = sqlite_id;
    }

    @Override
    public Date getTicked() {
        return ticked;
    }

    public void setTicked(Date ticked) {
        this.ticked = ticked;
    }

    @Override
    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public Integer getSelectedJobId() {
        return selectedJobId;
    }

    public void setSelectedJobId(Integer selectedJobId) {
        this.selectedJobId = selectedJobId;
    }

    public TickedJobsCtrlPojo() { }
    
    public TickedJobsCtrlPojo(TickedJobs tickedJobs){
        this.pk_id = tickedJobs.getPk_id();
        this.selectedJobId = tickedJobs.getJob().getPk_id();
        this.sqlite_id = tickedJobs.getSqlite_id();
        this.started = tickedJobs.isStarted();
        this.ticked = tickedJobs.getTicked();
    }

    public void setJob(JobsCtrlPojo job){
        this.job = job;
    }
    
    @Override
    public Jobs getJob() {
        if (jobs == null){
            this.job = null;
            
            return this.job;
        }
        
        if (jobs.isEmpty()){
            this.job = new JobsCtrlPojo();
            
            return this.job;
        }
        
        
        this.job = jobs.get(0);
        return this.job;
    }
}
