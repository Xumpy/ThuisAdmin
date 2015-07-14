/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao.sqlite.model;

import java.util.Date;

/**
 *
 * @author nicom
 */
public class TimeRecording {
    private Integer sqlite_id;
    private String ticked;
    private Integer started;

    public Integer getSqlite_id() {
        return sqlite_id;
    }

    public void setSqlite_id(Integer sqlite_id) {
        this.sqlite_id = sqlite_id;
    }

    public String getTicked() {
        return ticked;
    }

    public void setTicked(String ticked) {
        this.ticked = ticked;
    }

    public Integer getStarted() {
        return started;
    }

    public void setStarted(Integer started) {
        this.started = started;
    }
    
    
}
