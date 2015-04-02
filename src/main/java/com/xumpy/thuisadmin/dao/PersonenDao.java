/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao;

import com.xumpy.thuisadmin.dao.model.PersonenDaoPojo;
import java.util.List;

/**
 *
 * @author Nico
 */
public interface PersonenDao {
    void save(PersonenDaoPojo personen);
    void update(PersonenDaoPojo personen);
    void delete(PersonenDaoPojo personen);
    public Integer getNewPkId();
    public List<PersonenDaoPojo> findAllPersonen();
    public PersonenDaoPojo findPersoon(Integer persoonId);
    public PersonenDaoPojo findPersoonByUsername(String username);
}
