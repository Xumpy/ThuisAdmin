/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.controllers.pages;

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
    
    @RequestMapping(value = "admin/personen")
    public String viewPersonen(){
        return "admin/nieuwPersoon";
    }
    
    @RequestMapping(value = "admin/nieuwPersoon")
    public String viewNieuwPersoon(){
        return "admin/nieuwPersoon";
    }
    
    @RequestMapping(value = "admin/nieuwPersoon/{persoonId}")
    public String viewNieuwPersoon(@PathVariable Integer persoonId, Model model){
        model.addAttribute("pk_id", persoonId);
        
        return "admin/nieuwPersoon";
    }
    
    @RequestMapping(value = "admin/groepen")
    public String viewGroepen(){
        return "admin/groepen";
    }
    
    @RequestMapping(value = "admin/nieuwGroep")
    public String viewNieuwGroep(){
        return "admin/nieuwGroep";
    }
    
    @RequestMapping(value = "admin/nieuwGroep/{groepId}")
    public String viewNieuwGroep(@PathVariable Integer groepId, Model model){
        model.addAttribute("pk_id", groepId);
        
        return "admin/nieuwGroep";
    }
}
