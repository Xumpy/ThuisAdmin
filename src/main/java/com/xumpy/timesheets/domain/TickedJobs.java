/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.domain;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.Date;

/**
 *
 * @author nicom
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public interface TickedJobs {
    public Integer getPk_id();
    public Jobs getJob();
    public Integer getSqlite_id();
    public Date getTicked();
    public boolean isStarted();
}
