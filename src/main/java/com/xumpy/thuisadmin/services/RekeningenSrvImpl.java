/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services;

import com.xumpy.thuisadmin.dao.RekeningenDaoImpl;
import com.xumpy.thuisadmin.model.db.Rekeningen;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Nico
 */
public class RekeningenSrvImpl implements RekeningenSrv{

    @Autowired
    private RekeningenDaoImpl rekeningenDao;
    
    @Override
    public void save(Rekeningen rekeningen) {
        rekeningenDao.save(rekeningen);
    }

    @Override
    public void update(Rekeningen rekeningen) {
        rekeningenDao.update(rekeningen);
    }

    @Override
    public void delete(Rekeningen rekeningen) {
        rekeningenDao.delete(rekeningen);
    }

    @Override
    public List<Rekeningen> findAllRekeningen() {
        return rekeningenDao.findAllRekeningen();
    }
}
