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
    public List<? extends Groepen> findAllHoofdGroepen();
    public List<? extends Groepen> findAllHoofdGroepen(List<? extends Bedragen> bedragen);
    public List<? extends Groepen> findAllGroepen(Integer hoofdGroepId);
    public List<? extends Groepen> findAllGroepen();
    public Groepen findGroep(Integer groepId);
    public List<GroepenTree> groepTree(Integer selectedGroepId);
}
