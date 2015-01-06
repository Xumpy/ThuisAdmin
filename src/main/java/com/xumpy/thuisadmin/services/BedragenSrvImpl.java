/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services;

import com.xumpy.thuisadmin.dao.BedragenDaoImpl;
import com.xumpy.thuisadmin.model.db.Bedragen;
import com.xumpy.thuisadmin.model.db.Rekeningen;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Nico
 */
public class BedragenSrvImpl implements BedragenSrv{

    @Autowired
    private BedragenDaoImpl bedragenDao;
    
    @Override
    public void save(Bedragen bedragen) {
        bedragenDao.save(bedragen);
    }

    @Override
    public void update(Bedragen bedragen) {
        bedragenDao.update(bedragen);
    }

    @Override
    public void delete(Bedragen bedragen) {
        bedragenDao.delete(bedragen);
    }

    @Override
    public List<Bedragen> graphiekBedrag(Rekeningen rekening, Date beginDate, Date eindDate) {
        return bedragenDao.graphiekBedrag(rekening, beginDate, eindDate);
    }
}
