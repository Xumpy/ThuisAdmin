/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.controllers.rest;

import com.xumpy.thuisadmin.dao.model.GroepenDaoPojo;
import com.xumpy.thuisadmin.controllers.model.GroepenTree;
import com.xumpy.thuisadmin.domain.Groepen;
import com.xumpy.thuisadmin.services.GroepenSrv;
import com.xumpy.thuisadmin.services.model.GroepenSrvPojo;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
public class GroepenCtrl implements Serializable{
    @Autowired
    private GroepenSrv groepenSrv;
    
    @RequestMapping("/json/groepen")
    public @ResponseBody List<Groepen> fetchGroepen(){
        return groepenSrv.findAllGroepen();
    }
    
    @RequestMapping("/json/hoofdGroepen")
    public @ResponseBody List<Groepen> fetchHoofdGroepen(){
        return groepenSrv.findAllHoofdGroepen();
    }
    
    @RequestMapping("/json/hoofdGroep")
    public @ResponseBody Integer fetchHoofdGroep(@RequestBody GroepenDaoPojo groep){
        return -1;
    }
    
    @RequestMapping("/json/subGroepen/{hoofdGroepId}")
    public @ResponseBody List<Groepen> fetchSubGroepen(@PathVariable Integer hoofdGroepId){
        return groepenSrv.findAllGroepen(hoofdGroepId);
    }
    
    @RequestMapping("/json/groepen/{groepId}")
    public @ResponseBody Groepen fetchRekening(@PathVariable Integer groepId){
        return groepenSrv.findGroep(groepId);
    }
    
    @RequestMapping("/json/saveGroep")
    public @ResponseBody String saveGroep(@RequestBody GroepenDaoPojo groep){
        groepenSrv.save(groep);
        
        return "saved";
    }
    
    @RequestMapping("/json/deleteGroep")
    public @ResponseBody String deleteGroep(@RequestBody GroepenDaoPojo groep){
        groepenSrv.delete(groep);
        
        return "saved";
    }
    
    @RequestMapping("/json/groepTree")
    public @ResponseBody List<GroepenTree> groepTree(){
        return groepenSrv.groepTree(null);
    }
    
    @RequestMapping("/json/groepTree/{selectedGroepId}")
    public @ResponseBody List<GroepenTree> groepTree(@PathVariable Integer selectedGroepId){
        return groepenSrv.groepTree(selectedGroepId);
    }
}
