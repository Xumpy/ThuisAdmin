/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services;

import com.xumpy.thuisadmin.dao.model.PersonenDaoPojo;
import com.xumpy.thuisadmin.controllers.model.RegisterUserPage;
import java.util.List;

/**
 *
 * @author Nico
 */
public interface PersonenSrv {
    void save(PersonenDaoPojo personen);
    void update(PersonenDaoPojo personen);
    void delete(PersonenDaoPojo personen);
    public List<PersonenDaoPojo> findAllPersonen();
    public PersonenDaoPojo findPersoon(Integer persoonId);
    public PersonenDaoPojo createRegisterUser(RegisterUserPage registerUserPage);
    public PersonenDaoPojo getWhoAmI();
}
