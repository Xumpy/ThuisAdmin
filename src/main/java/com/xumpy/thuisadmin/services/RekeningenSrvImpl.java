/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services;

import com.xumpy.thuisadmin.dao.RekeningenDaoImpl;
import com.xumpy.thuisadmin.model.db.Personen;
import com.xumpy.thuisadmin.model.db.Rekeningen;
import com.xumpy.thuisadmin.model.view.RekeningBedragTotal;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nico
 */
@Service
public class RekeningenSrvImpl implements RekeningenSrv{

    @Autowired
    private RekeningenDaoImpl rekeningenDao;

    @Autowired
    private Personen persoon;
    
    @Override
    @Transactional(readOnly=false)
    public void save(Rekeningen rekeningen) {
        rekeningen.setPersoon(persoon);
        
        if (rekeningen.getPk_id() == null){
            rekeningen.setPk_id(rekeningenDao.getNewPkId());
            rekeningenDao.save(rekeningen);
        } else {
            rekeningenDao.update(rekeningen);
        }
    }

    @Override
    @Transactional(readOnly=false)
    public void update(Rekeningen rekeningen) {
        rekeningen.setPersoon(persoon);
        
        rekeningenDao.update(rekeningen);
    }

    @Override
    @Transactional(readOnly=false)
    public void delete(Rekeningen rekeningen) {
        rekeningenDao.delete(rekeningen);
    }

    @Override
    @Transactional
    public RekeningBedragTotal findAllRekeningen() {
        RekeningBedragTotal rekeningBedragTotal = new RekeningBedragTotal();
        List<Rekeningen> rekeningen = rekeningenDao.findAllRekeningen();
        
        BigDecimal totaal = new BigDecimal(0);
        
        for (Rekeningen rekening: rekeningen){
            totaal = totaal.add(rekening.getWaarde());
        }
        
        rekeningBedragTotal.setTotaal(totaal);
        rekeningBedragTotal.setRekeningen(rekeningen);
        
        return rekeningBedragTotal;
    }
    
    @Override
    @Transactional
    public Rekeningen findRekening(Integer rekeningId){
        return rekeningenDao.findRekening(rekeningId);
    }
}
