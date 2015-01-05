/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.controller;

import com.xumpy.thuisadmin.model.db.Rekeningen;
import com.xumpy.thuisadmin.services.RekeningenSrv;
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
public class FetchRekeningen {
    
    @Autowired
    private RekeningenSrv rekeningenSrv;
    
    @RequestMapping("/json/rekeningen")
    public @ResponseBody List<Rekeningen> fetchRekeningen(){
        return rekeningenSrv.findAllRekeningen();
    }
}
