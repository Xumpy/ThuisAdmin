/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao;

import com.xumpy.thuisadmin.dao.model.GroepenDaoPojo;
import com.xumpy.thuisadmin.controllers.model.GroepenTree;
import java.util.List;

/**
 *
 * @author Nico
 */
public interface GroepenDao {
    void save(GroepenDaoPojo groepen);
    void update(GroepenDaoPojo groepen);
    void delete(GroepenDaoPojo groepen);
    public Integer getNewPkId();
    public List<GroepenDaoPojo> findAllHoofdGroepen();
    public List<GroepenDaoPojo> findAllGroepen(Integer hoofdGroepId);
    public List<GroepenDaoPojo> findAllGroepen();
    public GroepenDaoPojo findGroep(Integer groepId);
}
