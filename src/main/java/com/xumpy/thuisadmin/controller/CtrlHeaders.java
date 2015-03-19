/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.controller;

import com.xumpy.thuisadmin.model.view.BeheerBedragenInp;
import com.xumpy.thuisadmin.model.view.FinanceHeader;
import java.io.Serializable;
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
public class CtrlHeaders implements Serializable{
    
    @Autowired
    private FinanceHeader financeHeader;

    @RequestMapping("/json/getFinanceHeader")
    public @ResponseBody FinanceHeader getFinanceHeader(){
        return financeHeader;
    }
    
    @RequestMapping("/json/setFinanceHeader")
    public @ResponseBody Integer setFinanceHeader(@RequestBody FinanceHeader financeHeader){
        this.financeHeader.setBeginDatum(financeHeader.getBeginDatum());
        this.financeHeader.setEindDatum(financeHeader.getEindDatum());
        this.financeHeader.setRekening(financeHeader.getRekening());
        
        return 1;
    }
}
