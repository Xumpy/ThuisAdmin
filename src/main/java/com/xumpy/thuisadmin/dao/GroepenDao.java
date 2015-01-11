/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao;

import com.xumpy.thuisadmin.model.db.Groepen;
import com.xumpy.thuisadmin.model.view.GroepenTree;
import java.util.List;

/**
 *
 * @author Nico
 */
public interface GroepenDao {
    void save(Groepen groepen);
    void update(Groepen groepen);
    void delete(Groepen groepen);
    public Integer getNewPkId();
    public List<Groepen> findAllHoofdGroepen();
    public List<Groepen> findAllGroepen(Integer hoofdGroepId);
    public List<Groepen> findAllGroepen();
    public Groepen findGroep(Integer groepId);
}
