/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services;

import com.xumpy.thuisadmin.dao.sqlite.model.TimeRecording;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

/**
 *
 * @author nico
 */
public interface TimesheetSrv {
    public void generateReport(Integer jobsGroupId, String month, HttpServletResponse response) throws IOException, JRException, SQLException, ParseException;
    public List<TimeRecording> getTimeRecordingFromWeb(String ip);
}
