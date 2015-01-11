/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services;

import com.xumpy.thuisadmin.model.db.Personen;
import java.util.List;

/**
 *
 * @author Nico
 */
public interface PersonenSrv {
    void save(Personen personen);
    void update(Personen personen);
    void delete(Personen personen);
    public List<Personen> findAllPersonen();
    public Personen findPersoon(Integer persoonId);
}
