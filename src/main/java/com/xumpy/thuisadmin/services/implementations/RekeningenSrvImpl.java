/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services.implementations;

import com.xumpy.security.model.InvoiceType;
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
import java.util.ArrayList;
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

    private Boolean nullSafeRekeningProfessional(Rekeningen rekening){
        if (rekening.getProfessional() == null){
            return false;
        }
        return rekening.getProfessional();
    }

    public Boolean isRekeningValid(Rekeningen rekening){
        if (userInfo.getInvoiceType().equals(InvoiceType.PERSONAL) && !nullSafeRekeningProfessional(rekening)) return true;
        if (userInfo.getInvoiceType().equals(InvoiceType.PROFESSIONAL) && nullSafeRekeningProfessional(rekening)) return true;
        if (userInfo.getInvoiceType().equals(InvoiceType.BOTH)) return true;

        return false;
    }

    private  List<Rekeningen> filterRekening(List<Rekeningen> lstRekeningen){
        List<Rekeningen> rekeningen = new ArrayList<>();

        for(Rekeningen rekening: lstRekeningen){
            if (isRekeningValid(rekening)) rekeningen.add(rekening);
        }

        return rekeningen;
    }

    @Override
    @Transactional(readOnly=false, value="transactionManager")
    public void save(NieuwRekening nieuwRekening) {
        RekeningenSrvPojo rekening = new RekeningenSrvPojo();
        
        rekening.setNaam(nieuwRekening.getNaam());
        rekening.setPersoon(new PersonenSrvPojo(userInfo.getPersoon()));
        rekening.setLaatst_bijgewerkt(nieuwRekening.getLaatst_bijgewerkt());
        rekening.setWaarde(BedragenSrvImpl.convertComma(nieuwRekening.getWaarde()));
        rekening.setBank(nieuwRekening.getBank());
        rekening.setRekeningNr(nieuwRekening.getRekeningNr());
        rekening.setClosed(nieuwRekening.getClosed());
        rekening.setProfessional(nieuwRekening.getProfessional());

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

    private RekeningBedragTotal buildRekeningBedragTotal(List<? extends Rekeningen> rekeningen){
        RekeningBedragTotal rekeningBedragTotal = new RekeningBedragTotal();
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
    public RekeningBedragTotal findAllRekeningen() {
        return buildRekeningBedragTotal(filterRekening(rekeningenDao.findAllRekeningen(userInfo.getPersoon().getPk_id())));
    }

    @Override
    @Transactional(value="transactionManager")
    public RekeningBedragTotal findAllOpenRekeningen() {
        return buildRekeningBedragTotal(filterRekening(rekeningenDao.findAllOpenRekeningen(userInfo.getPersoon().getPk_id())));
    }

    @Override
    @Transactional(value="transactionManager")
    public Rekeningen findRekening(Integer rekeningId){
        return rekeningenDao.findById(rekeningId).get();
    }
}
