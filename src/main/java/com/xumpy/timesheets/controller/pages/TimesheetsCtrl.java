/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.controller.pages;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
