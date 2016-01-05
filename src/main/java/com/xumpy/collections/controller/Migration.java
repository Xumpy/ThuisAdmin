/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.collections.controller;

import com.xumpy.collections.dao.crud.PersonCollectionStatusDao;
import com.xumpy.collections.domain.PersonCollectionStatus;
import com.xumpy.migration.collections.mysql.WriteJeromCSV;
import static com.xumpy.migration.collections.mysql.WriteJeromCSV.build;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author nico
 */
@Controller
@Scope("session")
public class Migration {
    @Autowired PersonCollectionStatusDao personCollectionStatusDao;
    
    private static Logger log = Logger.getLogger(Migration.class);
    
    @RequestMapping("/migration/migrate_jerom")
    public @ResponseBody String migrateJerom() throws ParseException, FileNotFoundException, IOException{
        FileInputStream inputStream = new FileInputStream(WriteJeromCSV.class.getClassLoader().getResource("testData/verzameling.csv").getPath());
        String content = IOUtils.toString(inputStream);

        List<WriteJeromCSV.Lijst> lstLijst = build(content);
        
        for(PersonCollectionStatus personCollectionStatus: personCollectionStatusDao.findAll()){
            log.info(personCollectionStatus.getName());
        }
        
        return "200";
    }
}
