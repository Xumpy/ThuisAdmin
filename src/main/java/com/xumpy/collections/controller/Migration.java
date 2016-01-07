/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.collections.controller;

import com.xumpy.collections.services.MigrationSrv;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author nico
 */
@Controller
@Scope("session")
public class Migration {
    @Autowired MigrationSrv migrationSrv;
    
    private static Logger log = Logger.getLogger(Migration.class);
    
    @RequestMapping("/migration/migrate_jerom")
    public @ResponseBody String migrateJerom() throws Exception{
        migrationSrv.migrate();
        
        return "200";
    }
}
