/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.controllers.rest;

import com.xumpy.thuisadmin.controllers.model.FilterReportBedragenInGroep;
import com.xumpy.thuisadmin.controllers.model.FinanceOverview;
import com.xumpy.thuisadmin.controllers.model.FinanceOverzichtGroep;
import com.xumpy.thuisadmin.controllers.model.OverzichtGroepBedragenInp;
import com.xumpy.thuisadmin.controllers.model.OverzichtGroepBedragenTotal;
import com.xumpy.thuisadmin.controllers.model.RekeningOverzicht;
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
public class FetchGraphiekOverview implements Serializable{
    @Autowired
    private BedragenSrv bedragenSrv;

    @Autowired public OverzichtGroepBedragenTotal overzichtGroepBedragenTotal;
    
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
            
            Integer showPublicGroep = financeOverview.isShowPublicGroep() ? 1 : 0;
            
            return bedragenSrv.graphiekOverzichtGroep(startDate, eindDate, showPublicGroep);  
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
            
            Integer showPublicGroep = overzichtGroepBedragenInp.isShowPublicGroep() ? 1 : 0;
            
            return bedragenSrv.rapportOverzichtGroepBedragen(overzichtGroepBedragenInp.getTypeGroepId(), 
                                                             overzichtGroepBedragenInp.getTypeGroepKostOpbrengst(),
                                                             startDate,
                                                             eindDate,
                                                             showPublicGroep,
                                                             overzichtGroepBedragenTotal);
        } else {
            return null;
        }
    }
    
    @RequestMapping("/json/report_overzicht_groep_bedragen_filter")
    public @ResponseBody OverzichtGroepBedragenTotal fetchReportOverzichtGroepBedragenWithFilter(@RequestBody FilterReportBedragenInGroep filterReportBedragenInGroep) {
        OverzichtGroepBedragenTotal overzicht = new OverzichtGroepBedragenTotal();
        OverzichtGroepBedragenTotal overzichtGroep = bedragenSrv.filterOverzichtGroepBedragenTotalFilter(overzichtGroepBedragenTotal, filterReportBedragenInGroep.getSearchTekst());
        overzicht.setOverzichtGroepBedragen(overzichtGroep.getOverzichtGroepBedragen());
        overzicht.setSomBedrag(overzichtGroep.getSomBedrag());
        
        return overzicht;
    }
}
