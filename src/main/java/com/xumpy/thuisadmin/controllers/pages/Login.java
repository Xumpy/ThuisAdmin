/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.controllers.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author nico
 */
@Controller
public class Login implements WebMvcConfigurer {
    @RequestMapping(value = "/login")
    public String login(){
        return "login";
    }
}
