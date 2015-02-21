/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services;

import com.xumpy.thuisadmin.dao.BedragenDaoImpl;
import com.xumpy.thuisadmin.dao.RekeningenDaoImpl;
import com.xumpy.thuisadmin.logic.BedragenLogic;
import com.xumpy.thuisadmin.model.db.Bedragen;
import com.xumpy.thuisadmin.model.db.Rekeningen;
import com.xumpy.thuisadmin.model.view.BeheerBedragenReport;
import com.xumpy.thuisadmin.model.view.FinanceOverzichtGroep;
import com.xumpy.thuisadmin.model.view.NieuwBedrag;
import com.xumpy.thuisadmin.model.view.OverzichtGroep;
import com.xumpy.thuisadmin.model.view.OverzichtGroepBedragen;
import com.xumpy.thuisadmin.model.view.OverzichtGroepBedragenTotal;
import com.xumpy.thuisadmin.model.view.RekeningOverzicht;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nico
 */
@Service
public class BedragenSrvImpl extends BedragenLogic implements BedragenSrv{

    @Autowired
    private BedragenDaoImpl bedragenDao;
    
    @Autowired
    private RekeningenDaoImpl rekeningenDao;
    
    static final Logger Log = Logger.getLogger(BedragenSrvImpl.class.getName());
    
    @Override
    @Transactional(readOnly=false)
    public void save(NieuwBedrag nieuwBedrag) {
        Bedragen bedragen = convertNieuwBedrag(nieuwBedrag);
        
        if (bedragen.getPk_id() == null){
            bedragen.setPk_id(bedragenDao.getNewPkId());
            
            bedragen = processRekeningBedrag(bedragen, INSERT);
            
            Rekeningen rekening = bedragen.getRekening();
            
            bedragenDao.save(bedragen);
            rekeningenDao.update(rekening);
        } else {
            update(nieuwBedrag);
        }
    }

    @Override
    @Transactional(readOnly=false)
    public void update(NieuwBedrag nieuwBedrag) {
        Bedragen bedragen = convertNieuwBedrag(nieuwBedrag);
        bedragen = processRekeningBedrag(bedragen, UPDATE);
        
        Rekeningen rekening = bedragen.getRekening();
        
        bedragenDao.update(bedragen);
        rekeningenDao.update(rekening);
    }

    @Override
    @Transactional(readOnly=false)
    public void delete(NieuwBedrag nieuwBedrag) {
        Bedragen bedragen = convertNieuwBedrag(nieuwBedrag);

        bedragen = processRekeningBedrag(bedragen, DELETE);
        Rekeningen rekening = bedragen.getRekening();

        bedragenDao.delete(bedragen);
        rekeningenDao.update(rekening);
    }

    @Override
    @Transactional
    public List<RekeningOverzicht> graphiekBedrag(Rekeningen rekening, Date beginDate, Date eindDate) {
        Map overzichtBedragen = bedragenDao.OverviewRekeningData(bedragenDao.BedragInPeriode(beginDate, eindDate), rekening);
        
        Iterator entries = overzichtBedragen.entrySet().iterator();
        List<RekeningOverzicht> rekeningOverzicht = new ArrayList<RekeningOverzicht>();
        while(entries.hasNext()){
            Entry entry = (Entry)entries.next();
            rekeningOverzicht.add(new RekeningOverzicht(entry.getKey(), entry.getValue()));
        }
        
        return rekeningOverzicht;
    }

    @Override
    @Transactional
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

    @Override
    @Transactional
    public OverzichtGroepBedragenTotal rapportOverzichtGroepBedragen(Integer typeGroepId, Integer typeGroepKostOpbrengst, Date beginDate, Date eindDate) {
        OverzichtGroepBedragenTotal overzichtGroepBedragenTotal = new OverzichtGroepBedragenTotal();
        
        Integer negatief = 0;
        BigDecimal somOverzicht = new BigDecimal(0);
        
        if (typeGroepKostOpbrengst.equals(1)){
            negatief = 0;
        } else {
            negatief = 1;
        }
        
        List<OverzichtGroepBedragen> overzichtGroepBedragen = bedragenDao.rapportOverzichtGroepBedragen(typeGroepId, negatief, beginDate, eindDate);
        
        for (OverzichtGroepBedragen overzicht: overzichtGroepBedragen){
            somOverzicht = somOverzicht.add(overzicht.getBedrag());
        }
        
        overzichtGroepBedragenTotal.setSomBedrag(somOverzicht);
        overzichtGroepBedragenTotal.setOverzichtGroepBedragen(overzichtGroepBedragen);
        
        return overzichtGroepBedragenTotal;
    }

    @Override
    @Transactional
    public List<BeheerBedragenReport> reportBedragen(Rekeningen rekening, Integer offset) {
        return bedragenDao.reportBedragen(rekening, offset);
    }

    @Override
    @Transactional
    public Bedragen findBedrag(Integer bedragId) {
        return bedragenDao.findBedrag(bedragId);
    }
}
