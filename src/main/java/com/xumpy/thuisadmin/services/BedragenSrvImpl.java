/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services;

import com.xumpy.thuisadmin.dao.BedragenDaoImpl;
import com.xumpy.thuisadmin.model.db.Bedragen;
import com.xumpy.thuisadmin.model.db.Rekeningen;
import com.xumpy.thuisadmin.model.view.FinanceOverzichtGroep;
import com.xumpy.thuisadmin.model.view.OverzichtGroep;
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

    @Override
    public FinanceOverzichtGroep graphiekOverzichtGroep(Date beginDate, Date eindDate) {
        FinanceOverzichtGroep financeOverzichtGroep = new FinanceOverzichtGroep();
        
        List<OverzichtGroep> overzichtGroep = bedragenDao.graphiekOverzichtGroep(beginDate, eindDate);
        
        Double totaal_kosten = 0.0;
        Double totaal_opbrengsten = 0.0;
        
        for (OverzichtGroep groep : overzichtGroep){
           totaal_kosten += groep.getTotaal_kosten();
           totaal_opbrengsten += groep.getTotaal_opbrengsten();
        }
        
        financeOverzichtGroep.setTotaal_kosten(totaal_kosten);
        financeOverzichtGroep.setTotaal_opbrengsten(totaal_opbrengsten);
        financeOverzichtGroep.setOverzichtGroep(overzichtGroep);
        
        return financeOverzichtGroep;
    }
}
