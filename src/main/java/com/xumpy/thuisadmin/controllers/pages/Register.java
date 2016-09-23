/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.controllers.pages;

import java.io.Serializable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author nicom
 */
@Controller
public class Register implements Serializable{
    @RequestMapping(value = "/register/register_user")
    public String viewOverview(){
        return "register/register_user";
    }
}
