/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services;

import com.xumpy.thuisadmin.model.db.Groepen;
import com.xumpy.thuisadmin.model.view.GroepenTree;
import java.util.List;

/**
 *
 * @author Nico
 */
public interface GroepenSrv {
    void save(Groepen groepen);
    void update(Groepen groepenn);
    void delete(Groepen groepen);
    public List<Groepen> findAllHoofdGroepen();
    public List<Groepen> findAllGroepen(Integer hoofdGroepId);
    public List<Groepen> findAllGroepen();
    public Groepen findGroep(Integer groepId);
    public List<GroepenTree> groepTree(Integer selectedGroepId);
}
