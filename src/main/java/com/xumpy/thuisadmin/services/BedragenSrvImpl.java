/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services;

import com.xumpy.security.model.UserInfo;
import com.xumpy.thuisadmin.dao.BedragenDaoImpl;
import static com.xumpy.thuisadmin.dao.BedragenDaoImpl.NEGATIEF;
import static com.xumpy.thuisadmin.dao.BedragenDaoImpl.POSITIEF;
import com.xumpy.thuisadmin.dao.GroepenDaoImpl;
import com.xumpy.thuisadmin.dao.RekeningenDaoImpl;
import com.xumpy.thuisadmin.logic.BedragenLogic;
import com.xumpy.thuisadmin.model.db.Bedragen;
import com.xumpy.thuisadmin.model.db.Groepen;
import com.xumpy.thuisadmin.model.db.Personen;
import com.xumpy.thuisadmin.model.db.Rekeningen;
import com.xumpy.thuisadmin.model.view.BeheerBedragenReportLst;
import com.xumpy.thuisadmin.model.view.FinanceOverzichtGroep;
import com.xumpy.thuisadmin.model.view.NieuwBedrag;
import com.xumpy.thuisadmin.model.view.OverzichtGroep;
import com.xumpy.thuisadmin.model.view.OverzichtGroepBedragen;
import com.xumpy.thuisadmin.model.view.OverzichtGroepBedragenTotal;
import com.xumpy.thuisadmin.model.view.RekeningOverzicht;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
public class BedragenSrvImpl extends BedragenLogic implements BedragenSrv, Serializable{

    @Autowired
    private BedragenDaoImpl bedragenDao;
    @Autowired
    private GroepenDaoImpl groepenDao;
    
    @Autowired
    private RekeningenDaoImpl rekeningenDao;
    
    @Autowired
    private UserInfo userInfo;
    
    @Autowired public OverzichtGroepBedragenTotal overzichtGroepBedragenTotal;
    
    static final Logger Log = Logger.getLogger(BedragenSrvImpl.class.getName());
    
    @Override
    @Transactional(readOnly=false)
    public void save(NieuwBedrag nieuwBedrag) {
        nieuwBedrag.setPersoon(userInfo.getPersoon());
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
        nieuwBedrag.setPersoon(userInfo.getPersoon());
        Bedragen bedragen = convertNieuwBedrag(nieuwBedrag);
        bedragen = processRekeningBedrag(bedragen, UPDATE);
        
        Rekeningen rekening = bedragen.getRekening();
        
        bedragenDao.update(bedragen);
        rekeningenDao.update(rekening);
    }

    @Override
    @Transactional(readOnly=false)
    public void delete(NieuwBedrag nieuwBedrag) {
        nieuwBedrag.setPersoon(userInfo.getPersoon());
        Bedragen bedragen = convertNieuwBedrag(nieuwBedrag);

        bedragen = processRekeningBedrag(bedragen, DELETE);
        Rekeningen rekening = bedragen.getRekening();

        bedragenDao.delete(bedragen);
        rekeningenDao.update(rekening);
    }

    @Override
    @Transactional
    public List<RekeningOverzicht> graphiekBedrag(Rekeningen rekening, Date beginDate, Date eindDate) {
        boolean showBedragPublicGroep = false;
        
        Map overzichtBedragen = OverviewRekeningData(bedragenDao.BedragInPeriode(beginDate, eindDate, rekening, showBedragPublicGroep));
        
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
    public FinanceOverzichtGroep graphiekOverzichtGroep(Date beginDate, Date eindDate, boolean showBedragPublicGroep) {
        
        FinanceOverzichtGroep financeOverzichtGroep = new FinanceOverzichtGroep();
        
        List<Bedragen> lstBedragen = bedragenDao.BedragInPeriode(beginDate, eindDate, null, showBedragPublicGroep);
        System.out.println(lstBedragen.size());
        
        Map<Groepen, Map> bedragenOverzicht = OverviewRekeningGroep(bedragenDao.BedragInPeriode(beginDate, eindDate, null, showBedragPublicGroep));
        
        BigDecimal totaalKosten = new BigDecimal(0);
        BigDecimal totaalOpbrengsten = new BigDecimal(0);
        
        List<OverzichtGroep> overzichtGroepen = new ArrayList<OverzichtGroep>();
        for (Entry entry: bedragenOverzicht.entrySet()){
            
            Groepen groep = (Groepen)entry.getKey();
            
            if (groep.getCodeId() == null){
                groep.setCodeId("NULL");
            }
            
            if (!groep.getCodeId().equals("INTER_REKENING")){
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
        }
        financeOverzichtGroep.setTotaal_kosten(totaalKosten.doubleValue());
        financeOverzichtGroep.setTotaal_opbrengsten(totaalOpbrengsten.doubleValue());
        
        financeOverzichtGroep.setOverzichtGroep(overzichtGroepen);
        
        return financeOverzichtGroep;
    }

    @Override
    @Transactional
    public OverzichtGroepBedragenTotal rapportOverzichtGroepBedragen(Integer typeGroepId, 
                                                                     Integer typeGroepKostOpbrengst, 
                                                                     Date beginDate, 
                                                                     Date eindDate,
                                                                     boolean showBedragPublicGroep) {
        Integer negatief = new Integer(0);
        
        if (typeGroepKostOpbrengst.equals(1)){
            negatief = 0;
        } else {
            negatief = 1;
        }
        
        List<Bedragen> lstBedragenInPeriode = bedragenDao.BedragInPeriode(beginDate, eindDate, null, showBedragPublicGroep);
        List<Bedragen> lstBedragen = getBedragenInGroep(lstBedragenInPeriode, groepenDao.findGroep(typeGroepId), negatief);
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
    public BeheerBedragenReportLst reportBedragen(BeheerBedragenReportLst beheerBedragenReportLst, Integer offset, Rekeningen rekening, String searchText) {
        beheerBedragenReportLst.setBeheerBedragenReport(bedragenDao.reportBedragen(rekening, offset, searchText));
        
        return beheerBedragenReportLst;
    }

    @Override
    @Transactional
    public Bedragen findBedrag(Integer bedragId) {
        return bedragenDao.findBedrag(bedragId);
    }
    
    public BigDecimal getTotalRekeningBedragen(List<Bedragen> bedragen){
        BigDecimal totaalRekeningen = new BigDecimal(0);
        List<Rekeningen> rekeningen = new ArrayList<Rekeningen>();
        
        for(Bedragen bedrag: bedragen){
            if (!rekeningen.contains(bedrag.getRekening())){
                rekeningen.add(bedrag.getRekening());
            }
        }
        
        for(Rekeningen rekening: rekeningen){
            totaalRekeningen = totaalRekeningen.add(rekening.getWaarde());
        }
        
        return totaalRekeningen;
    }
    
    public boolean isRekeningUnique(List<Bedragen> bedragen){
        List<Rekeningen> rekeningen = new ArrayList<Rekeningen>();
        
        for(Bedragen bedrag: bedragen){
            if (!rekeningen.contains(bedrag.getRekening())){
                rekeningen.add(bedrag.getRekening());
            }
        }
        
        if (rekeningen.size() == 1){
            return true;
        } else {
            return false;
        }
    }
    
    public Map OverviewRekeningData(List<Bedragen> bedragen){
        Map overviewRekeningData = new LinkedHashMap();

        Collections.sort(bedragen);
        
        BigDecimal rekeningStand;
        if (isRekeningUnique(bedragen)){
            rekeningStand = bedragenDao.getBedragAtDate(bedragen.get(0).getDatum(), bedragen.get(0).getRekening());
        } else {
            rekeningStand = bedragenDao.getBedragAtDate(bedragen.get(0).getDatum(), null);
        }
        
        overviewRekeningData.put(bedragen.get(0).getDatum(), rekeningStand);
        
        for (Integer i=1; i<bedragen.size(); i++){
            if (!bedragen.get(i).getDatum().equals(bedragen.get(0).getDatum())){
                if (bedragen.get(i).getGroep().getNegatief().equals(1)){
                    rekeningStand = rekeningStand.subtract(bedragen.get(i).getBedrag());
                }
                if (bedragen.get(i).getGroep().getNegatief().equals(0)){
                    rekeningStand = rekeningStand.add(bedragen.get(i).getBedrag());
                }
                overviewRekeningData.put(bedragen.get(i).getDatum(), rekeningStand);
            }
        }

        return overviewRekeningData;
    }

    public Map<Groepen, Map> OverviewRekeningGroep(List<Bedragen> bedragen){
        Map<Groepen, Map> overviewRekeningGroep = new LinkedHashMap<Groepen, Map>();
        
        bedragen = orderByGroup(bedragen);
        
        for (Bedragen bedrag: bedragen){
            Groepen hoofdGroep =  GroepenDaoImpl.getHoofdGroep(bedrag.getGroep());
            Map<String, BigDecimal> bedragInGroep = (Map)overviewRekeningGroep.get(hoofdGroep);
            
            if (bedragInGroep == null){
                bedragInGroep = new LinkedHashMap<String, BigDecimal>();
                bedragInGroep.put(POSITIEF, new BigDecimal(0));
                bedragInGroep.put(NEGATIEF, new BigDecimal(0));
            }
            
            BigDecimal bedragNegatief = (BigDecimal)bedragInGroep.get(NEGATIEF);
            BigDecimal bedragPositief = (BigDecimal)bedragInGroep.get(POSITIEF);
            
            if (bedrag.getGroep().getNegatief().equals(1)){
                bedragNegatief = bedragNegatief.add(bedrag.getBedrag());
            } else {
                bedragPositief = bedragPositief.add(bedrag.getBedrag());
            }
            
            bedragInGroep.put(POSITIEF, bedragPositief);
            bedragInGroep.put(NEGATIEF, bedragNegatief);
            
            overviewRekeningGroep.put(hoofdGroep, bedragInGroep);
        }
        
        return overviewRekeningGroep;
    }
    
    public List<Bedragen> getBedragenInGroep(List<Bedragen> bedragen, Groepen hoofdGroep, Integer negatief){
        List<Bedragen> bedragenInGroep = new ArrayList<Bedragen>();
        
        for(Bedragen bedrag: bedragen){
            if (GroepenDaoImpl.getHoofdGroep(bedrag.getGroep()).equals(hoofdGroep)  && bedrag.getGroep().getNegatief().equals(negatief)){
                bedragenInGroep.add(bedrag);
            }
        }
        
        return bedragenInGroep;
    }
    
    @Override
    public OverzichtGroepBedragenTotal filterOverzichtGroepBedragenTotalGroep(OverzichtGroepBedragenTotal overzichtGroepBedragenTotal, Groepen groep){
        List<OverzichtGroepBedragen> overzichtGroepBedragen = new ArrayList<OverzichtGroepBedragen>();
        BigDecimal newSomBedrag = new BigDecimal(0);
        
        OverzichtGroepBedragenTotal newOverzichtGroepBedragenTotal = new OverzichtGroepBedragenTotal();
        
        for (OverzichtGroepBedragen overzichtGroepBedrag: overzichtGroepBedragenTotal.getOverzichtGroepBedragen()){
            if (overzichtGroepBedrag.getType_naam().equals(groep.getNaam())){
                overzichtGroepBedragen.add(overzichtGroepBedrag);
                newSomBedrag = newSomBedrag.add(overzichtGroepBedrag.getBedrag());
            }
        }
        newOverzichtGroepBedragenTotal.setSomBedrag(newSomBedrag);
        newOverzichtGroepBedragenTotal.setOverzichtGroepBedragen(overzichtGroepBedragen);
        
        return newOverzichtGroepBedragenTotal;
    }
    
    @Override
    public OverzichtGroepBedragenTotal filterOverzichtGroepBedragenTotalFilter(OverzichtGroepBedragenTotal overzichtGroepBedragenTotal, String filter){
        List<OverzichtGroepBedragen> overzichtGroepBedragen = new ArrayList<OverzichtGroepBedragen>();
        BigDecimal newSomBedrag = new BigDecimal(0);
        
        OverzichtGroepBedragenTotal newOverzichtGroepBedragenTotal = new OverzichtGroepBedragenTotal();
        
        if (overzichtGroepBedragenTotal.getOverzichtGroepBedragen() != null){
            for (OverzichtGroepBedragen overzichtGroepBedrag: overzichtGroepBedragenTotal.getOverzichtGroepBedragen()){
                if (overzichtGroepBedrag.contains(filter)){
                    overzichtGroepBedragen.add(overzichtGroepBedrag);
                    newSomBedrag = newSomBedrag.add(overzichtGroepBedrag.getBedrag());
                }
            }
        }
        newOverzichtGroepBedragenTotal.setSomBedrag(newSomBedrag);
        newOverzichtGroepBedragenTotal.setOverzichtGroepBedragen(overzichtGroepBedragen);
        
        return newOverzichtGroepBedragenTotal;
    }
    
    public List<Bedragen> orderByGroup(List<Bedragen> bedragen){
        List<String> groupNames = new ArrayList<String>();
        
        for (Bedragen bedrag: bedragen){
            if (!groupNames.contains(bedrag.getGroep().getNaam())){
                groupNames.add(bedrag.getGroep().getNaam());
            }
        }
        
        Collections.sort(groupNames);
        
        List<Bedragen> orderBedragen = new ArrayList<Bedragen>();
        
        for (String groupName: groupNames){
            for (Bedragen bedrag: bedragen){
                if (bedrag.getGroep().getNaam().equals(groupName)){
                    orderBedragen.add(bedrag);
                }
            }
        }
        
        return orderBedragen;
    }
}
