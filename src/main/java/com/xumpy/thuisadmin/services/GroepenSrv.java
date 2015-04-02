/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services;

import com.xumpy.thuisadmin.dao.model.GroepenDaoPojo;
import com.xumpy.thuisadmin.controllers.model.GroepenTree;
import java.util.List;

/**
 *
 * @author Nico
 */
public interface GroepenSrv {
    void save(GroepenDaoPojo groepen);
    void update(GroepenDaoPojo groepenn);
    void delete(GroepenDaoPojo groepen);
    public List<GroepenDaoPojo> findAllHoofdGroepen();
    public List<GroepenDaoPojo> findAllGroepen(Integer hoofdGroepId);
    public List<GroepenDaoPojo> findAllGroepen();
    public GroepenDaoPojo findGroep(Integer groepId);
    public List<GroepenTree> groepTree(Integer selectedGroepId);
}
