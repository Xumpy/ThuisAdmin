/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao;

import com.xumpy.thuisadmin.dao.model.PersonenDaoPojo;
import com.xumpy.thuisadmin.model.Personen;
import java.util.List;

/**
 *
 * @author Nico
 */
public interface PersonenDao {
    Personen save(Personen personen);
    Personen update(Personen personen);
    Personen delete(Personen personen);
    public Integer getNewPkId();
    public List<Personen> findAllPersonen();
    public Personen findPersoon(Integer persoonId);
    public Personen findPersoonByUsername(String username);
}
