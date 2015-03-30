/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.controller;

import com.xumpy.thuisadmin.model.db.Rekeningen;
import com.xumpy.thuisadmin.model.view.NieuwRekening;
import com.xumpy.thuisadmin.model.view.RekeningBedragTotal;
import com.xumpy.thuisadmin.services.RekeningenSrv;
import java.io.Serializable;
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
public class FetchRekeningen implements Serializable{
    
    @Autowired
    private RekeningenSrv rekeningenSrv;
    
    @RequestMapping("/json/rekeningen")
    public @ResponseBody RekeningBedragTotal fetchRekeningen(){
        return rekeningenSrv.findAllRekeningen();
    }
    
    @RequestMapping("/json/rekeningen/{rekeningId}")
    public @ResponseBody Rekeningen fetchRekening(@PathVariable Integer rekeningId){
        return rekeningenSrv.findRekening(rekeningId);
    }
    
    @RequestMapping("/json/saveRekening")
    public @ResponseBody String saveRekening(@RequestBody NieuwRekening rekening){
        rekeningenSrv.save(rekening);
        
        return "saved";
    }
    
    @RequestMapping("/json/deleteRekening")
    public @ResponseBody String deleteRekening(@RequestBody Rekeningen rekening){
        rekeningenSrv.delete(rekening);
        
        return "saved";
    }
}
