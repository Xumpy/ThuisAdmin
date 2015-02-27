/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services;

import com.xumpy.thuisadmin.dao.BedragenDaoImpl;
import com.xumpy.thuisadmin.dao.GroepenDaoImpl;
import com.xumpy.thuisadmin.dao.RekeningenDaoImpl;
import com.xumpy.thuisadmin.logic.BedragenLogic;
import com.xumpy.thuisadmin.model.db.Bedragen;
import com.xumpy.thuisadmin.model.db.Groepen;
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
    private GroepenDaoImpl groepenDao;
    
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
        Map overzichtBedragen = bedragenDao.OverviewRekeningData(bedragenDao.BedragInPeriode(beginDate, eindDate, rekening));
        
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
        
        List<Bedragen> lstBedragen = bedragenDao.BedragInPeriode(beginDate, eindDate, null);
        System.out.println(lstBedragen.size());
        
        Map<Groepen, Map> bedragenOverzicht = bedragenDao.OverviewRekeningGroep(bedragenDao.BedragInPeriode(beginDate, eindDate, null));
        
        BigDecimal totaalKosten = new BigDecimal(0);
        BigDecimal totaalOpbrengsten = new BigDecimal(0);
        
        List<OverzichtGroep> overzichtGroepen = new ArrayList<OverzichtGroep>();
        for (Entry entry: bedragenOverzicht.entrySet()){
            
            Groepen groep = (Groepen)entry.getKey();
            
            OverzichtGroep overzichtGroep = new OverzichtGroep();
            overzichtGroep.setGroepId(groep.getPk_id());
            overzichtGroep.setNaam(groep.getNaam());
            
            Map<String, BigDecimal> bedragen = (Map<String, BigDecimal>)entry.getValue();
            overzichtGroep.setTotaal_kosten(bedragen.get(bedragenDao.NEGATIEF).doubleValue());
            overzichtGroep.setTotaal_opbrengsten(bedragen.get(bedragenDao.POSITIEF).doubleValue());
            
            overzichtGroepen.add(overzichtGroep);
            
            totaalKosten = totaalKosten.add(bedragen.get(bedragenDao.NEGATIEF));
            totaalOpbrengsten = totaalOpbrengsten.add(bedragen.get(bedragenDao.POSITIEF));
        }
        financeOverzichtGroep.setTotaal_kosten(totaalKosten.doubleValue());
        financeOverzichtGroep.setTotaal_opbrengsten(totaalOpbrengsten.doubleValue());
        
        financeOverzichtGroep.setOverzichtGroep(overzichtGroepen);
        
        return financeOverzichtGroep;
    }

    @Override
    @Transactional
    public OverzichtGroepBedragenTotal rapportOverzichtGroepBedragen(Integer typeGroepId, Integer typeGroepKostOpbrengst, Date beginDate, Date eindDate) {
        OverzichtGroepBedragenTotal overzichtGroepBedragenTotal = new OverzichtGroepBedragenTotal();
        Integer negatief = new Integer(0);
        
        if (typeGroepKostOpbrengst.equals(1)){
            negatief = 0;
        } else {
            negatief = 1;
        }
        
        List<Bedragen> lstBedragenInPeriode = bedragenDao.BedragInPeriode(beginDate, eindDate, null);
        List<Bedragen> lstBedragen = bedragenDao.getBedragenInGroep(lstBedragenInPeriode, groepenDao.findGroep(typeGroepId), negatief);
        List<OverzichtGroepBedragen> overzichtGroepBedragen = new ArrayList<OverzichtGroepBedragen>();
        
        BigDecimal somOverzicht = new BigDecimal(0);
        
        for(Bedragen bedrag: lstBedragen){
            OverzichtGroepBedragen overzichtGroepBedrag = new OverzichtGroepBedragen();
            overzichtGroepBedrag.setWithBedrag(bedrag);
            
            overzichtGroepBedragen.add(overzichtGroepBedrag);
            somOverzicht = somOverzicht.add(overzichtGroepBedrag.getBedrag());
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
