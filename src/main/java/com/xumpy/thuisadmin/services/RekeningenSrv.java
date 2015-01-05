/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services;

import com.xumpy.thuisadmin.model.db.Rekeningen;
import java.util.List;

/**
 *
 * @author Nico
 */
public interface RekeningenSrv {
    void save(Rekeningen rekeningen);
    void update(Rekeningen rekeningen);
    void delete(Rekeningen rekeningen);
    public List<Rekeningen> findAllRekeningen();
}
