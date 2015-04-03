/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao;

import com.xumpy.thuisadmin.model.Rekeningen;
import java.util.List;

/**
 *
 * @author Nico
 */
public interface RekeningenDao {
    void save(Rekeningen rekeningen);
    void update(Rekeningen rekeningen);
    void delete(Rekeningen rekeningen);
    public Integer getNewPkId();
    public Rekeningen findRekening(Integer rekeningId);
    public List<Rekeningen> findAllRekeningen();
}
