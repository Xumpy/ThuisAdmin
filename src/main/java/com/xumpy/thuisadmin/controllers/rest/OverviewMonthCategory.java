/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.controllers.rest;

import com.xumpy.thuisadmin.controllers.model.BarGraphic;
import com.xumpy.thuisadmin.controllers.model.OverzichtGroepBedragenTotal;
import com.xumpy.thuisadmin.controllers.model.finances.overviewMonthCategory.*;
import com.xumpy.thuisadmin.domain.Bedragen;
import com.xumpy.thuisadmin.domain.Groepen;
import com.xumpy.thuisadmin.services.BedragenSrv;
import com.xumpy.thuisadmin.services.GroepenSrv;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author nicom
 */
@Controller
@Scope("session")
public class OverviewMonthCategory implements Serializable{
    @Autowired GroepenSrv groepenSrv;
    @Autowired BedragenSrv bedragenSrv;
    @Autowired OverviewMonthCategoryInput overviewMonthCategoryInput;
    @Autowired public OverzichtGroepBedragenTotal overzichtGroepBedragenTotal;
    
    @RequestMapping("/json/overviewMonthCategoryHeader")
    public @ResponseBody OverviewMonthCategoryInput getOverviewMonthCategoryHeader(){
        return overviewMonthCategoryInput;
    }
    
    @RequestMapping("/json/fetchMainGroups")
    public @ResponseBody List<MainGroups> fetchMainGroups(){
        List<? extends Groepen> allMainGroups = groepenSrv.findAllHoofdGroepen();
        List<MainGroups> mainGroups = new ArrayList<MainGroups>();
        
        for (Groepen groep: allMainGroups){
            MainGroups mainGroup = new MainGroups();
            mainGroup.setName(groep.getNaam());
            mainGroup.setPk_id(groep.getPk_id());
            mainGroups.add(mainGroup);
        }
        
        return mainGroups;
    }
    
    @RequestMapping("/json/fetchOverviewMonthCategory")
    public @ResponseBody OverviewMonthCategoryResulst fetchMonthCategory(@RequestBody OverviewMonthCategoryInput overviewMonthCategory) throws ParseException{
        this.overviewMonthCategoryInput = overviewMonthCategory;
        
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy"); 
        Date startDate = dt.parse("01/" + overviewMonthCategory.getBeginDate());
        Calendar c = Calendar.getInstance();
        c.setTime(dt.parse("01/" + overviewMonthCategory.getEndDate()));
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endDate = c.getTime();
        
        List<Integer> mainGroups = new ArrayList<Integer>();
        for(MainGroupValue mainGroup: overviewMonthCategory.getMainGroupValues()){
            mainGroups.add(mainGroup.getPk_id());
        }
        
        List<? extends Bedragen> bedragen = bedragenSrv.filterBedragenWithMainGroup(bedragenSrv.selectBedragenInPeriode(startDate, endDate), mainGroups);
        OverviewMonthCategoryResulst overviewMonthCategoryResult = new OverviewMonthCategoryResulst(bedragen, groepenSrv, bedragenSrv);
        
        return overviewMonthCategoryResult;
    }

    @RequestMapping("/json/fetchOverviewMonth")
    public @ResponseBody BarGraphic fetchMonthBedragen(@RequestBody OverviewMonthCategoryInput overviewMonthCategory) throws ParseException{
        List<Object> headers = new ArrayList<Object>();

        headers.add("Maand");
        headers.add("Opbrengsten");
        headers.add("Kosten");

        BarGraphic barGraphic = new BarGraphic(headers, bedragenSrv.getBedragenInMonthRange(overviewMonthCategory.getBeginDate(), overviewMonthCategory.getEndDate()));
        return barGraphic;
    }

    @RequestMapping("/json/report_overzicht_groep_bedragen_per_maand")
    public @ResponseBody OverzichtGroepBedragenTotal fetchReportOverzichtGroepBedragenPerMonth(@RequestBody OverviewMonthCategoryReport overviewMonthCategoryReport) throws ParseException {
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy"); 
        Date startDate = dt.parse("01/" + overviewMonthCategoryReport.getDate());
        Calendar c = Calendar.getInstance();
        c.setTime(dt.parse("01/" + overviewMonthCategoryReport.getDate()));
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endDate = c.getTime();
        
        OverzichtGroepBedragenTotal overzicht = new OverzichtGroepBedragenTotal();
        OverzichtGroepBedragenTotal overzichtGroep = bedragenSrv.rapportOverzichtGroepBedragen(overviewMonthCategoryReport.getMainGroup(), 
                                                                                               startDate,
                                                                                               endDate,
                                                                                               0,
                                                                                               overzichtGroepBedragenTotal);
        overzicht.setOverzichtGroepBedragen(overzichtGroep.getOverzichtGroepBedragen());
        overzicht.setSomBedrag(overzichtGroep.getSomBedrag());
        
        return overzicht;
    }
}
