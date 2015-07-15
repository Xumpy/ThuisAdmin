/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.controller.pages;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.xumpy.thuisadmin.controllers.model.NieuwDocument;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author nicom
 */
@Controller
public class TimesheetsCtrl {
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
    
    @RequestMapping(value="timesheets/saveSSHSQLite")
    public String saveSSHSQLite(@RequestParam("ip") String ip) throws IOException{
        try{
        
            JSch jsch = new JSch();
            Session session = null;
            session = jsch.getSession("root",ip,22);
            session.setPassword("pcat3900");
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            ChannelSftp channel = null;
            channel = (ChannelSftp)session.openChannel("sftp");
            channel.connect();

            File localFile = new File("/tmp/timeRecording.db");


            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(localFile));
            channel.get("/data/data/com.dynamicg.timerecording/files/timeRecording.db", stream);
            
            channel.disconnect();
            session.disconnect();
            
            stream.close();
        } catch (Exception ex){
            System.out.println("Error occured while fetching the file from ssh");
        }
        return "redirect:/timesheets/importTimeRecordings";
    }
}
