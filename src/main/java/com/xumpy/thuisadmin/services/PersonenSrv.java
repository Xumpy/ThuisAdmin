/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services;

import com.xumpy.thuisadmin.controllers.model.RegisterUserPage;
import com.xumpy.thuisadmin.domain.Personen;
import java.util.List;

/**
 *
 * @author Nico
 */
public interface PersonenSrv {
    Personen save(Personen personen);
    Personen delete(Personen personen);
    public List<? extends Personen> findAllPersonen();
    public Personen findPersoon(Integer persoonId);
    public Personen findPersoonByUsername(String username);
    public Personen createRegisterUser(RegisterUserPage registerUserPage);
    public Personen getWhoAmI();
}
