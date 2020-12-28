/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services;

import com.xumpy.thuisadmin.dao.sqlite.model.TimeRecording;
import net.sf.jasperreports.engine.JRException;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
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
