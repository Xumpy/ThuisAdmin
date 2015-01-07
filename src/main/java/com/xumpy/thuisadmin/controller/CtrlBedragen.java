/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.controller;

import com.xumpy.thuisadmin.model.db.Rekeningen;
import com.xumpy.thuisadmin.model.view.BeheerBedragenReport;
import com.xumpy.thuisadmin.services.BedragenSrv;
import java.text.ParseException;
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
public class CtrlBedragen {
    @Autowired
    private BedragenSrv bedragenSrv;
    
    @RequestMapping("/json/fetch_bedragen")
    public @ResponseBody List<BeheerBedragenReport> fetchBedragen(@RequestBody Rekeningen rekening) throws ParseException{
        return bedragenSrv.reportBedragen(rekening);
    }
}
