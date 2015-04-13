/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services;

import com.xumpy.thuisadmin.controllers.model.GroepenTree;
import com.xumpy.thuisadmin.domain.Bedragen;
import com.xumpy.thuisadmin.domain.Groepen;
import java.util.List;

/**
 *
 * @author Nico
 */
public interface GroepenSrv {
    Groepen save(Groepen groepen);
    Groepen delete(Groepen groepen);
    public List<Groepen> findAllHoofdGroepen();
    public List<Groepen> findAllHoofdGroepen(List<Bedragen> bedragen);
    public List<Groepen> findAllGroepen(Integer hoofdGroepId);
    public List<Groepen> findAllGroepen();
    public Groepen findGroep(Integer groepId);
    public List<GroepenTree> groepTree(Integer selectedGroepId);
}
