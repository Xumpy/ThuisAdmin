/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services;

import com.xumpy.thuisadmin.dao.sqlite.model.TimeRecording;

import java.io.OutputStream;
import java.util.List;

/**
 *
 * @author nico
 */
public interface TimesheetSrv {
    public OutputStream getTimesheet(Integer jobsGroupId, String month, OutputStream outputStream);
    public List<TimeRecording> getTimeRecordingFromWeb(String ip);
}
