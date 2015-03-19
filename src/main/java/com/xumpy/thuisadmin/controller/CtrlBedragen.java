/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.controller;

import com.xumpy.thuisadmin.logic.BeheerBedragenButtons;
import com.xumpy.thuisadmin.model.db.Bedragen;
import com.xumpy.thuisadmin.model.view.BeheerBedragenInp;
import com.xumpy.thuisadmin.model.view.BeheerBedragenReportLst;
import com.xumpy.thuisadmin.model.view.NieuwBedrag;
import com.xumpy.thuisadmin.services.BedragenSrv;
import java.io.Serializable;
import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Nico
 */
@Controller
@Scope("session")
public class CtrlBedragen implements Serializable{
    @Autowired
    private BedragenSrv bedragenSrv;
    
    @Autowired
    private BeheerBedragenInp beheerBedragenInp;
    
    @Autowired
    private BeheerBedragenReportLst beheerBedragenReportLst;
    
    @RequestMapping("/json/fetch_beheer_bedragen")
    public @ResponseBody BeheerBedragenInp fetchBeheerBedragen() throws ParseException{
        return this.beheerBedragenInp;
    }
    
    @RequestMapping("/json/fetch_bedragen")
    public @ResponseBody BeheerBedragenReportLst fetchBedragen(@RequestBody BeheerBedragenInp beheerBedragenInp) throws ParseException{
        if (this.beheerBedragenInp.getOffset().equals(beheerBedragenInp.getOffset())) beheerBedragenInp.setOffset(0);
        this.beheerBedragenInp = beheerBedragenInp;

        beheerBedragenReportLst = bedragenSrv.reportBedragen(beheerBedragenReportLst, beheerBedragenInp.getOffset(), beheerBedragenInp.getRekening(), beheerBedragenInp.getZoekOpdracht());
        beheerBedragenReportLst = BeheerBedragenButtons.setButtons(beheerBedragenReportLst, beheerBedragenInp);
        
        return beheerBedragenReportLst;
    }
    
    @RequestMapping("/json/save_bedrag")
    public @ResponseBody String saveBedragen(@RequestBody NieuwBedrag bedrag) throws ParseException, Exception{
        bedragenSrv.save(bedrag);
        
        return "1";
    }
    
    @RequestMapping("/json/find_bedrag/{bedragId}")
    public @ResponseBody Bedragen findBedrag(@PathVariable Integer bedragId) throws ParseException{
        return bedragenSrv.findBedrag(bedragId);
    }
    
    @RequestMapping("/json/delete_bedrag")
    public @ResponseBody String deleteBedrag(@RequestBody NieuwBedrag bedrag) throws ParseException, Exception{
        bedragenSrv.delete(bedrag);
        
        return "1";
    }
}
