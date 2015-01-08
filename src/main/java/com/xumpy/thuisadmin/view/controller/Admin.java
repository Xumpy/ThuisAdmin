/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.view.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Nico
 */
@Controller
public class Admin {
    @RequestMapping(value = "admin/rekeningen")
    public String viewOverzichtPerGroep(){
        return "admin/rekeningen";
    }
    
    @RequestMapping(value = "admin/nieuwRekening")
    public String viewNieuwRekening(){
        return "admin/nieuwRekening";
    }
    
    @RequestMapping(value = "admin/nieuwRekening/{rekeningId}")
    public String viewNieuwRekening(@PathVariable Integer rekeningId, Model model){
        model.addAttribute("pk_id", rekeningId);
        
        return "admin/nieuwRekening";
    }
}
