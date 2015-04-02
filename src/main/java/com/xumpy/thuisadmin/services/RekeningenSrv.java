/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services;

import com.xumpy.thuisadmin.dao.model.RekeningenDaoPojo;
import com.xumpy.thuisadmin.controllers.model.NieuwRekening;
import com.xumpy.thuisadmin.controllers.model.RekeningBedragTotal;

/**
 *
 * @author Nico
 */
public interface RekeningenSrv {
    void save(NieuwRekening rekeningen);
    public void update(RekeningenDaoPojo rekeningen);
    void delete(RekeningenDaoPojo rekeningen);
    public RekeningBedragTotal findAllRekeningen();
    public RekeningenDaoPojo findRekening(Integer rekeningId);
}
