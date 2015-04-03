/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services.implementations;

import com.xumpy.security.model.UserInfo;
import com.xumpy.thuisadmin.controllers.model.BeheerBedragenReport;
import com.xumpy.thuisadmin.dao.implementations.BedragenDaoImpl;
import static com.xumpy.thuisadmin.dao.implementations.BedragenDaoImpl.NEGATIEF;
import static com.xumpy.thuisadmin.dao.implementations.BedragenDaoImpl.POSITIEF;
import com.xumpy.thuisadmin.dao.implementations.GroepenDaoImpl;
import com.xumpy.thuisadmin.dao.implementations.RekeningenDaoImpl;
import com.xumpy.thuisadmin.services.logic.BedragenLogic;
import com.xumpy.thuisadmin.dao.model.BedragenDaoPojo;
import com.xumpy.thuisadmin.dao.model.RekeningenDaoPojo;
import com.xumpy.thuisadmin.controllers.model.BeheerBedragenReportLst;
import com.xumpy.thuisadmin.controllers.model.FinanceOverzichtGroep;
import com.xumpy.thuisadmin.controllers.model.NieuwBedrag;
import com.xumpy.thuisadmin.controllers.model.OverzichtGroep;
import com.xumpy.thuisadmin.controllers.model.OverzichtGroepBedragen;
import com.xumpy.thuisadmin.controllers.model.OverzichtGroepBedragenTotal;
import com.xumpy.thuisadmin.controllers.model.RekeningOverzicht;
import com.xumpy.thuisadmin.model.Bedragen;
import com.xumpy.thuisadmin.model.Groepen;
import com.xumpy.thuisadmin.model.Rekeningen;
import com.xumpy.thuisadmin.services.BedragenSrv;
import com.xumpy.thuisadmin.services.model.BedragenSrvPojo;
import com.xumpy.thuisadmin.services.model.GroepenSrvPojo;
import com.xumpy.thuisadmin.services.model.PersonenSrvPojo;
import com.xumpy.thuisadmin.services.model.RekeningenSrvPojo;
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
    public Bedragen save(NieuwBedrag nieuwBedrag) {
        nieuwBedrag.setPersoon(new PersonenSrvPojo(userInfo.getPersoon()));
        BedragenDaoPojo bedragen = new BedragenDaoPojo(convertNieuwBedrag(nieuwBedrag));
        
        if (bedragen.getPk_id() == null){
            bedragen.setPk_id(bedragenDao.getNewPkId());
            
            bedragen = new BedragenDaoPojo(processRekeningBedrag(bedragen, INSERT));
        } else {
            bedragen = new BedragenDaoPojo(processRekeningBedrag(bedragen, UPDATE));
        }
        rekeningenDao.update(bedragen.getRekening());
        bedragenDao.save(bedragen);
        
        return bedragen;
    }

    @Override
    @Transactional(readOnly=false)
    public Bedragen delete(NieuwBedrag nieuwBedrag) {
        nieuwBedrag.setPersoon(new PersonenSrvPojo(userInfo.getPersoon()));
        Bedragen bedragen = convertNieuwBedrag(nieuwBedrag);
        bedragen = processRekeningBedrag(bedragen, DELETE);
        
        Rekeningen rekening = bedragen.getRekening();

        rekeningenDao.update(rekening);
        bedragenDao.delete(bedragen);
        
        return bedragen;
    }

    @Override
    @Transactional
    public List<RekeningOverzicht> graphiekBedrag(Rekeningen rekening, Date beginDate, Date eindDate) {
        boolean showBedragPublicGroep = false;
        List<Bedragen> lstBedragenSrv = bedragenDao.BedragInPeriode(beginDate, eindDate, rekening, showBedragPublicGroep);
        
        Map overzichtBedragen = OverviewRekeningData(lstBedragenSrv);
        
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
        
        List<Bedragen> bedragInPeriode = bedragenDao.BedragInPeriode(beginDate, eindDate, null, showBedragPublicGroep);
        List<Bedragen> overviewRekeningGroep = bedragenDao.BedragInPeriode(beginDate, eindDate, null, showBedragPublicGroep);
        
        
        FinanceOverzichtGroep financeOverzichtGroep = new FinanceOverzichtGroep();
        
        List<Bedragen> lstBedragen = bedragInPeriode;
        System.out.println(lstBedragen.size());
        
        Map<Groepen, Map<String, BigDecimal>> bedragenOverzicht = OverviewRekeningGroep(overviewRekeningGroep);
        
        BigDecimal totaalKosten = new BigDecimal(0);
        BigDecimal totaalOpbrengsten = new BigDecimal(0);
        
        List<OverzichtGroep> overzichtGroepen = new ArrayList<OverzichtGroep>();
        for (Entry entry: bedragenOverzicht.entrySet()){
            
            GroepenSrvPojo groep = new GroepenSrvPojo((Groepen)entry.getKey());
            
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
        List<Bedragen> lstBedragenInPeriode = bedragenDao.BedragInPeriode(beginDate, eindDate, null, showBedragPublicGroep);
        
        Groepen groepenSrv = groepenDao.findGroep(typeGroepId);
        
        Integer negatief = new Integer(0);
        
        if (typeGroepKostOpbrengst.equals(1)){
            negatief = 0;
        } else {
            negatief = 1;
        }
        
        List<Bedragen> lstBedragen = getBedragenInGroep(lstBedragenInPeriode, groepenSrv, negatief);
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
        
        List<BeheerBedragenReport> beheerBedragenReport = new ArrayList<BeheerBedragenReport>();
        
        for (Bedragen bedrag: bedragenDao.reportBedragen(rekening, offset, searchText)){
            beheerBedragenReport.add(new BeheerBedragenReport(bedrag));
        }
        
        beheerBedragenReportLst.setBeheerBedragenReport(beheerBedragenReport);
        
        return beheerBedragenReportLst;
    }

    @Override
    @Transactional
    public Bedragen findBedrag(Integer bedragId) {
        return bedragenDao.findBedrag(bedragId);
    }
    
    public Map OverviewRekeningData(List<Bedragen> bedragen){
        Map overviewRekeningData = new LinkedHashMap();

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
    
    @Override
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
    
    public Map<Groepen, Map<String, BigDecimal>> OverviewRekeningGroep(List<Bedragen> bedragen){
        Map<Groepen, Map<String, BigDecimal>> overviewRekeningGroep = new LinkedHashMap<Groepen, Map<String, BigDecimal>>();
        
        for (Bedragen bedrag: bedragen){
            if (bedrag.getGroep().getCodeId() == null || !bedrag.getGroep().getCodeId().equals("INTER_REKENING")){
                Groepen hoofdGroep = GroepenSrvImpl.getHoofdGroep(bedrag.getGroep());
                Map<String, BigDecimal> bedragInGroep = (Map<String, BigDecimal>)overviewRekeningGroep.get(hoofdGroep);

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
        }
        
        return overviewRekeningGroep;
    }
    
    public List<Bedragen> getBedragenInGroep(List<Bedragen> bedragen, Groepen hoofdGroep, Integer negatief){
        List<Bedragen> bedragenInGroep = new ArrayList<Bedragen>();
        
        for(Bedragen bedrag: bedragen){
            if (GroepenSrvImpl.getHoofdGroep(bedrag.getGroep()).equals(hoofdGroep)  && bedrag.getGroep().getNegatief().equals(negatief)){
                bedragenInGroep.add(bedrag);
            }
        }
        
        return bedragenInGroep;
    }
}
