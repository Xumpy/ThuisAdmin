 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.controllers.rest;

import com.xumpy.thuisadmin.controllers.model.BedragAccountingCtrlPojo;
import com.xumpy.thuisadmin.controllers.model.BeheerBedragenInp;
import com.xumpy.thuisadmin.controllers.model.BeheerBedragenReportLst;
import com.xumpy.thuisadmin.controllers.model.NieuwBedrag;
import com.xumpy.thuisadmin.dao.implementations.BedragAccountingDaoImpl;
import com.xumpy.thuisadmin.domain.BedragAccounting;
import com.xumpy.thuisadmin.services.BedragenSrv;
import com.xumpy.thuisadmin.services.implementations.BedragenSrvImpl;
import com.xumpy.thuisadmin.services.model.BedragenSrvPojo;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired BedragAccountingDaoImpl bedragAccountingDao;

    @RequestMapping("/json/fetch_beheer_bedragen")
    public @ResponseBody BeheerBedragenInp fetchBeheerBedragen() throws ParseException{
        this.beheerBedragenInp.setCourantValue(bedragenSrv.getCourantValue());

        return this.beheerBedragenInp;
    }
    
    @RequestMapping("/json/fetch_bedragen")
    public @ResponseBody BeheerBedragenReportLst fetchBedragen(@RequestBody BeheerBedragenInp beheerBedragenInp) throws ParseException{
        if (this.beheerBedragenInp.getOffset().equals(beheerBedragenInp.getOffset())) beheerBedragenInp.setOffset(0);
        this.beheerBedragenInp = beheerBedragenInp;

        beheerBedragenReportLst = bedragenSrv.reportBedragen(beheerBedragenReportLst, beheerBedragenInp.getOffset(), beheerBedragenInp.getRekening(), beheerBedragenInp.getZoekOpdracht(), beheerBedragenInp.getValidAccountyBedrag());
        beheerBedragenReportLst = BedragenSrvImpl.setButtons(beheerBedragenReportLst, beheerBedragenInp);
        
        return beheerBedragenReportLst;
    }
    
    @RequestMapping("/json/save_bedrag")
    public @ResponseBody String saveBedragen(@RequestBody NieuwBedrag bedrag) throws ParseException, Exception{
        bedragenSrv.save(bedrag);
        
        return "1";
    }
    
    @RequestMapping("/json/find_bedrag/{bedragId}")
    public @ResponseBody BedragenSrvPojo findBedrag(@PathVariable Integer bedragId) throws ParseException{
        return new BedragenSrvPojo(bedragenSrv.findBedrag(bedragId));
    }
    
    @RequestMapping("/json/delete_bedrag")
    public @ResponseBody String deleteBedrag(@RequestBody NieuwBedrag bedrag) throws ParseException, Exception{
        bedragenSrv.delete(bedrag);
        
        return "1";
    }

    @RequestMapping("/json/find_bedrag_accounting/{bedragId}")
    public @ResponseBody List<BedragAccountingCtrlPojo> findBedragAccounting(@PathVariable Integer bedragId) throws ParseException{
        List<BedragAccountingCtrlPojo> bedragAccountingCtrlPojos = new ArrayList<>();

        for(BedragAccounting bedragAccounting: bedragAccountingDao.getAccountantBedragenByBedrag(bedragId)){
            bedragAccountingCtrlPojos.add(new BedragAccountingCtrlPojo(bedragAccounting));
        }

        return bedragAccountingCtrlPojos;
    }
}
