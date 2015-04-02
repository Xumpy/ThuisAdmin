/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao;

import com.xumpy.thuisadmin.dao.model.RekeningenDaoPojo;
import java.util.List;

/**
 *
 * @author Nico
 */
public interface RekeningenDao {
    void save(RekeningenDaoPojo rekeningen);
    void update(RekeningenDaoPojo rekeningen);
    void delete(RekeningenDaoPojo rekeningen);
    public Integer getNewPkId();
    public RekeningenDaoPojo findRekening(Integer rekeningId);
    public List<RekeningenDaoPojo> findAllRekeningen();
}
