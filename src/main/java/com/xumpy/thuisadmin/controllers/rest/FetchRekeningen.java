/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.controllers.rest;

import com.xumpy.thuisadmin.dao.model.RekeningenDaoPojo;
import com.xumpy.thuisadmin.controllers.model.NieuwRekening;
import com.xumpy.thuisadmin.controllers.model.RekeningBedragTotal;
import com.xumpy.thuisadmin.services.RekeningenSrv;
import com.xumpy.thuisadmin.services.model.RekeningenSrvPojo;
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
    public @ResponseBody RekeningenSrvPojo fetchRekening(@PathVariable Integer rekeningId){
        return new RekeningenSrvPojo(rekeningenSrv.findRekening(rekeningId));
    }
    
    @RequestMapping("/json/saveRekening")
    public @ResponseBody String saveRekening(@RequestBody NieuwRekening rekening){
        rekeningenSrv.save(rekening);
        
        return "saved";
    }
    
    @RequestMapping("/json/deleteRekening")
    public @ResponseBody String deleteRekening(@RequestBody RekeningenDaoPojo rekening){
        rekeningenSrv.delete(rekening);
        
        return "saved";
    }
}
