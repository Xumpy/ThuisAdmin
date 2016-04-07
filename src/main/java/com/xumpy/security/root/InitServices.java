/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.security.root;

import org.springframework.context.annotation.ComponentScan;

/**
 *
 * @author nico
 */
@ComponentScan({"com.xumpy.thuisadmin.services.*", "com.xumpy.collections.services.*", "com.xumpy.timesheets.services.*"})
public class InitServices {
    
}
