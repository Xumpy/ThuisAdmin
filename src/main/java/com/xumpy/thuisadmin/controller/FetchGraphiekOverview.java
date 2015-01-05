/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.controller;

import com.xumpy.thuisadmin.model.db.Bedragen;
import com.xumpy.thuisadmin.services.BedragenSrv;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Nico
 */

@Controller
public class FetchGraphiekOverview {
    @Autowired
    private BedragenSrv bedragenSrv;
    
    @RequestMapping("/json/graphiek_overview")
    public @ResponseBody List<Bedragen> fetchGraphiekOverview() throws ParseException{
        
        String strStartDate = "01/01/2000";
        String strEindDate = "01/01/2015";
        
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        
        Date startDate = format.parse(strStartDate);
        Date eindDate = format.parse(strEindDate);
        
        return bedragenSrv.graphiekBedrag(1, startDate, eindDate);
    }
}
