/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.controller;

import com.xumpy.thuisadmin.model.db.Bedragen;
import com.xumpy.thuisadmin.model.view.FinanceOverview;
import com.xumpy.thuisadmin.model.view.FinanceOverzichtGroep;
import com.xumpy.thuisadmin.model.view.OverzichtGroepBedragenInp;
import com.xumpy.thuisadmin.model.view.OverzichtGroepBedragenTotal;
import com.xumpy.thuisadmin.model.view.RekeningOverzicht;
import com.xumpy.thuisadmin.services.BedragenSrv;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
 * @author Nico
 */

@Controller
@Scope("session")
public class FetchGraphiekOverview{
    @Autowired
    private BedragenSrv bedragenSrv;
    
    @RequestMapping("/json/graphiek_overview")
    public @ResponseBody List<RekeningOverzicht> fetchGraphiekOverview(@RequestBody FinanceOverview financeOverview) throws ParseException{
        if (financeOverview.getBeginDatum() != null){
            String strStartDate = financeOverview.getBeginDatum();
            String strEindDate = financeOverview.getEindDatum();
        
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        
            Date startDate = format.parse(strStartDate);
            Date eindDate = format.parse(strEindDate);
            
            return bedragenSrv.graphiekBedrag(financeOverview.getRekening(), startDate, eindDate);
        } else {
            return null;
        }
    }
    
    @RequestMapping("/json/graphiek_overzicht_per_groep")
    public @ResponseBody FinanceOverzichtGroep fetchGraphiekOverzichtPerGroep(@RequestBody FinanceOverview financeOverview) throws ParseException{
        if (financeOverview.getBeginDatum() != null){
            String strStartDate = financeOverview.getBeginDatum();
            String strEindDate = financeOverview.getEindDatum();

            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            Date startDate = format.parse(strStartDate);
            Date eindDate = format.parse(strEindDate);

            return bedragenSrv.graphiekOverzichtGroep(startDate, eindDate);  
        } else {
            return null;
        }
    }
    
    @RequestMapping("/json/report_overzicht_groep_bedragen")
    public @ResponseBody OverzichtGroepBedragenTotal fetchReportOverzichtGroepBedragen(@RequestBody OverzichtGroepBedragenInp overzichtGroepBedragenInp)
            throws ParseException{
        if (overzichtGroepBedragenInp.getBeginDatum() != null){
            String strStartDate = overzichtGroepBedragenInp.getBeginDatum();
            String strEindDate = overzichtGroepBedragenInp.getEindDatum();

            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            Date startDate = format.parse(strStartDate);
            Date eindDate = format.parse(strEindDate);
            
            return bedragenSrv.rapportOverzichtGroepBedragen(overzichtGroepBedragenInp.getTypeGroepId(), 
                                                             overzichtGroepBedragenInp.getTypeGroepKostOpbrengst(),
                                                             startDate,
                                                             eindDate);
        } else {
            return null;
        }
    }
}
