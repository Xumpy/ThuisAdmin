/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.dao.model;

import com.xumpy.timesheets.domain.Jobs;
import com.xumpy.timesheets.domain.TickedJobs;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author nicom
 */

@Entity
@Table(name="TA_TICKED_JOBS")
public class TickedJobsDaoPojo implements TickedJobs{
    @Id
    @Column(name="PK_ID")
    private Integer pk_id;
    
    @ManyToOne
    @JoinColumn(name="FK_JOB_ID")
    private JobsDaoPojo job;
    
    @Column(name="SQLITE_ID")
    private Integer sqlite_id;
    
    @Column(name="TICKED")
    private Date ticked;
    
    @Column(name="STARTED")
    private boolean started;
    
    @Override
    public Integer getPk_id() {
        return pk_id;
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

    @Override
    public Jobs getJob() {
        return job;
    }

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    public void setJob(JobsDaoPojo job) {
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
    
    public TickedJobsDaoPojo(){ }
    
    public TickedJobsDaoPojo(TickedJobs tickedJobs){
        this.job = new JobsDaoPojo(tickedJobs.getJob());
        this.pk_id = tickedJobs.getPk_id();
        this.sqlite_id = tickedJobs.getSqlite_id();
        this.started = tickedJobs.getStarted();
        this.ticked = tickedJobs.getTicked();
    }
}
