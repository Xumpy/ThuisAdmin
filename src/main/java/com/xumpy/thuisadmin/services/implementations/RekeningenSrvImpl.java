/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services.implementations;

import com.xumpy.security.model.UserInfo;
import com.xumpy.thuisadmin.dao.implementations.RekeningenDaoImpl;
import com.xumpy.thuisadmin.controllers.model.NieuwRekening;
import com.xumpy.thuisadmin.controllers.model.RekeningBedragTotal;
import com.xumpy.thuisadmin.dao.model.RekeningenDaoPojo;
import com.xumpy.thuisadmin.domain.Rekeningen;
import com.xumpy.thuisadmin.services.RekeningenSrv;
import com.xumpy.thuisadmin.services.model.PersonenSrvPojo;
import com.xumpy.thuisadmin.services.model.RekeningenSrvPojo;
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
public class RekeningenSrvImpl implements RekeningenSrv, Serializable{

    @Autowired
    private RekeningenDaoImpl rekeningenDao;

    @Autowired
    private UserInfo userInfo;
    
    @Override
    @Transactional(readOnly=false, value="transactionManager")
    public void save(NieuwRekening nieuwRekening) {
        RekeningenSrvPojo rekening = new RekeningenSrvPojo();
        
        rekening.setNaam(nieuwRekening.getNaam());
        rekening.setPersoon(new PersonenSrvPojo(userInfo.getPersoon()));
        rekening.setLaatst_bijgewerkt(nieuwRekening.getLaatst_bijgewerkt());
        rekening.setWaarde(BedragenSrvImpl.convertComma(nieuwRekening.getWaarde()));
        
        if (nieuwRekening.getPk_id() == null){
            rekening.setPk_id(rekeningenDao.getNewPkId());
            rekeningenDao.save(new RekeningenDaoPojo(rekening));
        } else {
            rekening.setPk_id(nieuwRekening.getPk_id());
            rekeningenDao.save(new RekeningenDaoPojo(rekening));
        }
    }

    @Override
    @Transactional(readOnly=false, value="transactionManager")
    public void update(Rekeningen rekeningen) {
        rekeningenDao.save(new RekeningenDaoPojo(rekeningen));
    }

    @Override
    @Transactional(readOnly=false, value="transactionManager")
    public void delete(Rekeningen rekeningen) {
        rekeningenDao.delete(new RekeningenDaoPojo(rekeningen));
    }

    @Override
    @Transactional(value="transactionManager")
    public RekeningBedragTotal findAllRekeningen() {
        RekeningBedragTotal rekeningBedragTotal = new RekeningBedragTotal();
        List<? extends Rekeningen> rekeningen = rekeningenDao.findAllRekeningen(userInfo.getPersoon().getPk_id());
        
        BigDecimal totaal = new BigDecimal(0);
        
        for (Rekeningen rekening: rekeningen){
            totaal = totaal.add(rekening.getWaarde());
        }
        
        rekeningBedragTotal.setTotaal(totaal);
        rekeningBedragTotal.setRekeningen(rekeningen);
        
        return rekeningBedragTotal;
    }
    
    @Override
    @Transactional(value="transactionManager")
    public Rekeningen findRekening(Integer rekeningId){
        return rekeningenDao.findOne(rekeningId);
    }
}
