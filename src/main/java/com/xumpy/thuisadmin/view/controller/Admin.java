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
public class Admin {
    @RequestMapping(value = "admin/rekeningen")
    public String viewOverzichtPerGroep(){
        return "admin/rekeningen";
    }
}
