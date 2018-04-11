/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services;

import com.xumpy.thuisadmin.controllers.model.NieuwRekening;
import com.xumpy.thuisadmin.controllers.model.RekeningBedragTotal;
import com.xumpy.thuisadmin.domain.Rekeningen;

/**
 *
 * @author Nico
 */
public interface RekeningenSrv {
    void save(NieuwRekening rekeningen);
    public void update(Rekeningen rekeningen);
    void delete(Rekeningen rekeningen);
    public RekeningBedragTotal findAllRekeningen();
    public Rekeningen findRekening(Integer rekeningId);
    public RekeningBedragTotal findAllOpenRekeningen();
}
