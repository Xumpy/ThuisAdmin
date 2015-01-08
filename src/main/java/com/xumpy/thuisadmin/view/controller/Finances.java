/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Nico
 */
@Controller
public class Finances {
    @RequestMapping(value = "finances/overview")
    public String viewOverview(){
        return "finances/overview";
    }
    
    @RequestMapping(value = "finances/beheerBedragen")
    public String viewBeheerBedragen(){
        return "finances/beheerBedragen";
    }
    
    @RequestMapping(value = "finances/documenten")
    public String viewDocumenten(){
        return "finances/documenten";
    }
    
    @RequestMapping(value = "finances/nieuwBedrag")
    public String viewNieuwBedrag(){
        return "finances/nieuwBedrag";
    }
    
    @RequestMapping(value = "finances/overzichtPerGroep")
    public String viewOverzichtPerGroep(){
        return "finances/overzichtPerGroep";
    }
}
