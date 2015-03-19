/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.controller;

import com.xumpy.thuisadmin.model.db.Personen;
import com.xumpy.thuisadmin.services.PersonenSrv;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Nico
 */
@Controller
@Scope("session")
public class PersonenCtrl implements Serializable{
    @Autowired
    private PersonenSrv personenSrv;
    
    @RequestMapping("/json/personen")
    public @ResponseBody List<Personen> fetchPersonen(){
        return personenSrv.findAllPersonen();
    }
    
    @RequestMapping("/json/personen/{rekeningId}")
    public @ResponseBody Personen fetchRekening(@PathVariable Integer rekeningId){
        return personenSrv.findPersoon(rekeningId);
    }
    
    @RequestMapping("/json/savePersoon")
    public @ResponseBody String saveRekening(@RequestBody Personen persoon){
        personenSrv.save(persoon);
        
        return "saved";
    }
    
    @RequestMapping("/json/deletePersoon")
    public @ResponseBody String deleteRekening(@RequestBody Personen persoon){
        personenSrv.delete(persoon);
        
        return "saved";
    }
}
