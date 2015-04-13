/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services.implementations;

import com.xumpy.security.model.UserInfo;
import com.xumpy.thuisadmin.dao.implementations.GroepenDaoImpl;
import com.xumpy.thuisadmin.dao.model.GroepenDaoPojo;
import com.xumpy.thuisadmin.controllers.model.GroepenTree;
import com.xumpy.thuisadmin.domain.Bedragen;
import com.xumpy.thuisadmin.domain.Groepen;
import com.xumpy.thuisadmin.services.GroepenSrv;
import com.xumpy.thuisadmin.services.model.GroepenSrvPojo;
import com.xumpy.thuisadmin.services.model.PersonenSrvPojo;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nico
 */
@Service
public class GroepenSrvImpl implements GroepenSrv{

    @Autowired
    private GroepenDaoImpl groepenDao;
    
    @Autowired
    private UserInfo userInfo;
    
    @Override
    @Transactional(readOnly=false)
    public Groepen save(Groepen groepen) {
        GroepenSrvPojo groepenSrvPojo = new GroepenSrvPojo(groepen);
        if (groepenSrvPojo.getPk_id() == null){
            groepenSrvPojo.setPk_id(groepenDao.getNewPkId());
            groepenSrvPojo.setPersoon(new PersonenSrvPojo(userInfo.getPersoon()));
            groepenDao.save(groepenSrvPojo);
        } else {
            if (groepen.getPersoon().getUsername().equals(userInfo.getPersoon().getUsername())){
                groepenDao.save(groepenSrvPojo);
            }
        }
        
        return groepenSrvPojo;
    }

    @Override
    @Transactional(readOnly=false)
    public Groepen delete(Groepen groepen) {
        groepenDao.delete(groepen);
        return groepen;
    }

    @Override
    @Transactional
    public List<Groepen> findAllGroepen() {
        return groepenDao.findAllGroepen();
    }

    @Override
    @Transactional
    public Groepen findGroep(Integer groepId) {
        return groepenDao.findGroep(groepId);
    }

    @Override
    @Transactional
    public List<Groepen> findAllHoofdGroepen() {
        return groepenDao.findAllHoofdGroepen();
    }

    @Override
    public List<Groepen> findAllHoofdGroepen(List<Bedragen> bedragen){
        List<Groepen> groepen = new ArrayList<Groepen>();
        System.out.println(bedragen.size());
            
        for (Bedragen bedrag: bedragen){
            if (!groepen.contains(getHoofdGroep(bedrag.getGroep()))){
                groepen.add(getHoofdGroep(bedrag.getGroep()));
            }
        }
        
        return groepen;
    }
    
    @Override
    @Transactional
    public List<Groepen> findAllGroepen(Integer hoofdGroepId) {
        return groepenDao.findAllGroepen(hoofdGroepId);
    }

    private Boolean selectedGroepInHoofdGroep(Groepen groep, Groepen checkGroep){
        
        if (checkGroep.getPk_id().equals(groep.getPk_id())){
                return true;
        }
        
        if (groep.getHoofdGroep() != null){
            return selectedGroepInHoofdGroep(groep.getHoofdGroep(), checkGroep);
        } else {
          return false;
        }   
    }
    
    private List<GroepenTree> selectSubGroep(List<Groepen> allGroepen, Groepen hoofdGroep, Groepen selectedGroep){
        List<GroepenTree> lstGroepenTree = new ArrayList<GroepenTree>();
        List<Groepen> lstGroepen = new ArrayList<Groepen>();
        
        for(Groepen groep: allGroepen){
            if (groep.getHoofdGroep() != null && groep.getHoofdGroep().equals(hoofdGroep)){
                lstGroepen.add(groep);
            }
        }
        
        for (Groepen groep: lstGroepen){
            GroepenTree groepenTree = new GroepenTree();
            
            groepenTree.setGroep(groep);
            if (selectedGroep != null){
                
                if (selectedGroepInHoofdGroep(selectedGroep, groep)){
                    groepenTree.setCollapsed("");
                } else {
                    groepenTree.setCollapsed("true");
                }
                
                if (selectedGroep.getPk_id().equals(groep.getPk_id())){
                    groepenTree.setSelected("selected");
                } else {
                    groepenTree.setSelected("");
                }
            } else {
                groepenTree.setSelected("");
                groepenTree.setCollapsed("true");
            }
            
            groepenTree.setSubGroep(selectSubGroep(allGroepen, groep, selectedGroep));
            
            lstGroepenTree.add(groepenTree);
        }
        
        return lstGroepenTree;
    }
 
    @Override
    @Transactional
    public List<GroepenTree> groepTree(Integer selectedGroepId) {
        Groepen selectedGroep = null;
        List<Groepen> lstGroepen = groepenDao.findAllGroepen();
        
        List<GroepenTree> lstGroepTree = new ArrayList<GroepenTree>();
        
        List<Groepen> lstHoofdGroepen = new ArrayList<Groepen>();
        
        for (Groepen groep: lstGroepen){
            if (groep.getHoofdGroep() == null){
                lstHoofdGroepen.add(groep);
            }
        }
        
        for(Groepen hoofdGroep: lstHoofdGroepen){
            GroepenTree groepenTree = new GroepenTree();
            
            groepenTree.setGroep(hoofdGroep);
                    
            if (selectedGroepId != null) {
                selectedGroep = groepenDao.findGroep(selectedGroepId);
                
                if (selectedGroepInHoofdGroep(selectedGroep, hoofdGroep)){
                    groepenTree.setCollapsed("");
                } else {
                    groepenTree.setCollapsed("true");
                }
                
                if (selectedGroep.getPk_id().equals(hoofdGroep.getPk_id())){
                    groepenTree.setSelected("selected");
                } else {
                    groepenTree.setSelected("");
                }
            } else {
                groepenTree.setSelected("");
                groepenTree.setCollapsed("true");
            }
            
            groepenTree.setSubGroep(selectSubGroep(lstGroepen, hoofdGroep, selectedGroep));
            
            lstGroepTree.add(groepenTree);
        }
        
        return lstGroepTree;
    }
    
    
    public static Groepen getHoofdGroep(Groepen groep){
        if (groep.getHoofdGroep() == null){
            return groep;
        } else {
            Groepen hoofdGroep = groep.getHoofdGroep();
            
            while(true){
                if (hoofdGroep.getHoofdGroep() != null){
                    if (hoofdGroep.getHoofdGroep().getPk_id() != null){
                        hoofdGroep = hoofdGroep.getHoofdGroep();
                    } else {
                        return hoofdGroep;
                    }
                } else {
                    return hoofdGroep;
                }
            }
        }
    }
}
