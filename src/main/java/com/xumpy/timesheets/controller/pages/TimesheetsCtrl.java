/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.controller.pages;

import com.xumpy.thuisadmin.dao.sqlite.LoadSqlLite;
import com.xumpy.timesheets.services.TimesheetSrv;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.ParseException;
import javax.servlet.http.HttpServletResponse;

import com.xumpy.timesheets.services.session.SessionTimesheet;
import net.sf.jasperreports.engine.JRException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author nicom
 */
@Controller
public class TimesheetsCtrl {
    @Autowired TimesheetSrv timesheetSrv;
    @Autowired SessionTimesheet sessionTimesheet;

    private Logger log = Logger.getLogger(TimesheetsCtrl.class);
    
    @RequestMapping(value = "timesheets/overview")
    public String timesheetsOverview(){
        return "timesheets/overview";
    }
    
    @RequestMapping(value = "timesheets/group")
    public String timesheetsGroup(){
        return "timesheets/group";
    }
    
    @RequestMapping(value = "timesheets/editGroup")
    public String timesheetsEditGroup(){
        return "timesheets/editGroup";
    }
    
    @RequestMapping(value = "timesheets/addGroups")
    public String timesheetsAddGroups(){
        return "timesheets/addGroups";
    }
    
    @RequestMapping(value = "timesheets/editGroup/{groupId}")
    public String viewNieuwBedrag(@PathVariable Integer groupId, Model model){
        model.addAttribute("pk_id", groupId);
        
        return "timesheets/editGroup";
    }
    
    @RequestMapping(value = "timesheets/graphics")
    public String viewGraphics(){
        return "timesheets/graphics";
    }
    
    @RequestMapping(value = "timesheets/companies")
    public String viewCompanies(){
        return "timesheets/companies";
    }
    
    @RequestMapping(value = "timesheets/editCompanies")
    public String editCompanies(){
        return "timesheets/editCompanies";
    }
    
    @RequestMapping(value = "timesheets/editCompanies/{companyId}")
    public String editCompanies(@PathVariable Integer companyId, Model model){
        model.addAttribute("pk_id", companyId);
        
        return "timesheets/editCompanies";
    }
    
    @RequestMapping(value = "timesheets/importTimeRecordings")
    public String importTimeRecordings(){
        return "timesheets/importTimeRecordings";
    }
    
    @RequestMapping(value="timesheets/saveSQLite")
    public String saveSQLiteDB(@RequestParam("file") MultipartFile file) throws IOException{
        if (!file.isEmpty()) {
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("/tmp/timeRecording.db")));
            stream.write(file.getBytes());
            stream.close();
        }
        
        return "redirect:/timesheets/importTimeRecordings";
    }
    
    @RequestMapping(params="ssh", value="timesheets/saveSSHSQLite")
    public String saveSSHSQLite(@RequestParam("ip") String ip) throws IOException{
        Runtime.getRuntime().exec(new String[] {"/bin/sh","-c", "sshpass -p 'pcat3900' scp -r root@" + ip + ":/data/data/com.dynamicg.timerecording/files/timeRecording.db /tmp/timeRecording.db"});
        try {
            sessionTimesheet.setTimesheetTable(LoadSqlLite.loadTimeRecordings("/tmp/timeRecording.db"));
        } catch (ClassNotFoundException exception) {
            throw new RuntimeException(exception);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }

        return "redirect:/timesheets/importTimeRecordings";
    }

    @RequestMapping(params="web", value="timesheets/saveSSHSQLite")
    public String saveWebService(@RequestParam("ip") String ip) throws IOException{
        sessionTimesheet.setTimesheetTable(timesheetSrv.getTimeRecordingFromWeb(ip));

        return "redirect:/timesheets/importTimeRecordings";
    }

    @RequestMapping(value="timesheets/printTimesheet", method = RequestMethod.GET)
    public @ResponseBody void fetchDocumentBlob(@RequestParam Integer jobsGroupId, @RequestParam String month, HttpServletResponse response) throws IOException, SQLException, JRException, ParseException {
        timesheetSrv.generateReport(jobsGroupId, month, response);
    }

    @RequestMapping(value="timesheets/newGroupPrice/{groupId}")
    public String newGroupPrice(@PathVariable Integer groupId, Model model){
        model.addAttribute("groupId", groupId);

        return "timesheets/editGroupPrice";
    }

    @RequestMapping(value="timesheets/editGroup/editGroupPrice/{groupPriceId}")
    public String editGroupPrice(@PathVariable Integer groupPriceId, Model model){
        model.addAttribute("pk_id", groupPriceId);

        return "timesheets/editGroupPrice";
    }

    @RequestMapping(value="timesheets/absence")
    public String absence(){
        return "timesheets/absence";
    }
}
