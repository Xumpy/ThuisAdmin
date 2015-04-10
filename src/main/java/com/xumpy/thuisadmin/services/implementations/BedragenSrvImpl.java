/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services.implementations;

import com.xumpy.security.model.UserInfo;
import com.xumpy.thuisadmin.controllers.model.BeheerBedragenInp;
import com.xumpy.thuisadmin.controllers.model.BeheerBedragenReport;
import com.xumpy.thuisadmin.dao.implementations.BedragenDaoImpl;
import static com.xumpy.thuisadmin.dao.implementations.BedragenDaoImpl.NEGATIEF;
import static com.xumpy.thuisadmin.dao.implementations.BedragenDaoImpl.POSITIEF;
import com.xumpy.thuisadmin.dao.implementations.GroepenDaoImpl;
import com.xumpy.thuisadmin.dao.implementations.RekeningenDaoImpl;
import com.xumpy.thuisadmin.dao.model.BedragenDaoPojo;
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
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nico
 */
@Service
public class BedragenSrvImpl implements BedragenSrv, Serializable{

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

    public static final String UPDATE = "UPDATE";
    public static final String INSERT = "INSERT";
    public static final String DELETE = "DELETE";
    
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
        
        FinanceOverzichtGroep financeOverzichtGroep = new FinanceOverzichtGroep();
        
        List<Bedragen> lstBedragen = bedragInPeriode;
        
        Map<Groepen, Map<String, BigDecimal>> bedragenOverzicht = OverviewRekeningGroep(bedragInPeriode);
        
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
                                                                     Date beginDate, 
                                                                     Date eindDate,
                                                                     boolean showBedragPublicGroep) {
        List<Bedragen> lstBedragenInPeriode = bedragenDao.BedragInPeriode(beginDate, eindDate, null, showBedragPublicGroep);
        
        Groepen groepenSrv = groepenDao.findGroep(typeGroepId);

        Integer negatief = new Integer(0);
        
        List<Bedragen> lstBedragenNeg = getBedragenInGroep(lstBedragenInPeriode, groepenSrv, 1);
        List<Bedragen> lstBedragenPos = getBedragenInGroep(lstBedragenInPeriode, groepenSrv, 0);

        List<OverzichtGroepBedragen> overzichtGroepBedragen = new ArrayList<OverzichtGroepBedragen>();
        
        BigDecimal somOverzicht = new BigDecimal(0);
        
        for(Bedragen bedrag: lstBedragenNeg){
            System.out.println(bedrag);
            OverzichtGroepBedragen overzichtGroepBedrag = new OverzichtGroepBedragen();
            
            BedragenSrvPojo bedragSrvPojo = new BedragenSrvPojo(bedrag);
            bedragSrvPojo.setBedrag(bedrag.getBedrag().multiply(new BigDecimal(-1)));
            overzichtGroepBedrag.setWithBedrag(bedragSrvPojo);
            
            overzichtGroepBedragen.add(overzichtGroepBedrag);
            somOverzicht = somOverzicht.add(bedragSrvPojo.getBedrag());
        }
        
        for(Bedragen bedrag: lstBedragenPos){
            System.out.println(bedrag);
            OverzichtGroepBedragen overzichtGroepBedrag = new OverzichtGroepBedragen();
            overzichtGroepBedrag.setWithBedrag(bedrag);
            
            overzichtGroepBedragen.add(overzichtGroepBedrag);
            somOverzicht = somOverzicht.add(bedrag.getBedrag());
        }
        
        overzichtGroepBedragenTotal.setSomBedrag(somOverzicht);
        overzichtGroepBedragenTotal.setOverzichtGroepBedragen(overzichtGroepBedragen);

        return overzichtGroepBedragenTotal;
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
        System.out.println(somOverzicht);
            
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
    
    public static BigDecimal convertComma(String bedrag){
        if (bedrag.contains(",")){
            bedrag = bedrag.replace(".", "");
            bedrag = bedrag.replace(",", ".");
        } else {
            if (bedrag.indexOf(".", bedrag.indexOf(".") + 1) != -1){
                bedrag = bedrag.replace(".", "");
            }
        }

        NumberFormat nf = NumberFormat.getInstance(new Locale("US"));
        BigDecimal bigDecimalBedrag = new BigDecimal(0);
        try {
            bigDecimalBedrag = new BigDecimal(nf.parse(bedrag).doubleValue());
        } catch (ParseException ex) {
            Logger.getLogger(BedragenSrvImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        bigDecimalBedrag = bigDecimalBedrag.setScale(2, BigDecimal.ROUND_HALF_UP);
        
        return bigDecimalBedrag;
    }
    
    public Bedragen convertNieuwBedrag(NieuwBedrag nieuwBedrag){
        BedragenSrvPojo bedragen = new BedragenSrvPojo();
        
        bedragen.setPk_id(nieuwBedrag.getPk_id());
        bedragen.setDatum(nieuwBedrag.getDatum());
        bedragen.setGroep(nieuwBedrag.getGroep());
        bedragen.setOmschrijving(nieuwBedrag.getOmschrijving());
        bedragen.setPersoon(nieuwBedrag.getPersoon());
        bedragen.setRekening(nieuwBedrag.getRekening());

        BigDecimal bigDecimalBedrag = convertComma(nieuwBedrag.getBedrag());
        
        bedragen.setBedrag(bigDecimalBedrag);
        
        return bedragen;
    }
    
    public Bedragen processRekeningBedrag(Bedragen bedrag, String transaction){
        BedragenSrvPojo bedragenSrvPojo = new BedragenSrvPojo(bedrag);
        RekeningenSrvPojo rekening = new RekeningenSrvPojo(bedrag.getRekening());
        
        if (transaction.equals(INSERT)){
            if (bedragenSrvPojo.getGroep().getNegatief().equals(1)){
                rekening.setWaarde(rekening.getWaarde().subtract(bedragenSrvPojo.getBedrag()));
            } else {
                rekening.setWaarde(rekening.getWaarde().add(bedragenSrvPojo.getBedrag()));
            }
        } 
        
        if (transaction.equals(UPDATE)){
            BedragenSrvPojo oldBedrag = new BedragenSrvPojo(bedragenDao.findBedrag(bedragenSrvPojo.getPk_id()));
            
            if (!oldBedrag.getRekening().equals(bedragenSrvPojo.getRekening())){
                rekening = new RekeningenSrvPojo(moveBedragToRekening(oldBedrag, rekening));
            }
            System.out.println(rekening.getWaarde());
            
            if (bedragenSrvPojo.getGroep().getNegatief().equals(1)){
                if (oldBedrag.getGroep().getNegatief().equals(1)){
                    rekening.setWaarde((rekening.getWaarde().add(oldBedrag.getBedrag())));
                } else {
                    rekening.setWaarde((rekening.getWaarde().subtract(oldBedrag.getBedrag())));
                }
                rekening.setWaarde((rekening.getWaarde().subtract(bedragenSrvPojo.getBedrag())));
            } else {
                if (oldBedrag.getGroep().getNegatief().equals(1)){
                    rekening.setWaarde((rekening.getWaarde().add(oldBedrag.getBedrag())));
                } else {
                    rekening.setWaarde((rekening.getWaarde().subtract(oldBedrag.getBedrag())));
                }
                rekening.setWaarde((rekening.getWaarde().add(bedragenSrvPojo.getBedrag())));
            }
        }

        if (transaction.equals(DELETE)){
            if (bedragenSrvPojo.getGroep().getNegatief().equals(1)){
                rekening.setWaarde((rekening.getWaarde().add(bedragenSrvPojo.getBedrag())));
            } else {
                rekening.setWaarde((rekening.getWaarde().subtract(bedragenSrvPojo.getBedrag())));
            }
        
        }
        
        bedragenSrvPojo.setRekening(rekening);

        return bedragenSrvPojo;
    }

    public Rekeningen moveBedragToRekening(BedragenSrvPojo bedrag, Rekeningen rekening2) {
        RekeningenSrvPojo rekening2SrvPojo = new RekeningenSrvPojo(rekening2);
        
        if (bedrag.getGroep().getNegatief().equals(0)){
            bedrag.getRekening().setWaarde(bedrag.getRekening().getWaarde().subtract(bedrag.getBedrag()));
            rekeningenDao.save(bedrag.getRekening());
            rekening2 = rekeningenDao.findRekening(rekening2.getPk_id());
            rekening2SrvPojo.setWaarde(rekening2.getWaarde().add(bedrag.getBedrag()));
        } else {
            bedrag.getRekening().setWaarde(bedrag.getRekening().getWaarde().add(bedrag.getBedrag()));
            rekeningenDao.save(bedrag.getRekening());
            rekening2 = rekeningenDao.findRekening(rekening2.getPk_id());
            rekening2SrvPojo.setWaarde(rekening2.getWaarde().subtract(bedrag.getBedrag()));
        }
        return rekening2SrvPojo;
    }
    
    public static BeheerBedragenReportLst setButtons(BeheerBedragenReportLst beheerBedragenReportLst, BeheerBedragenInp beheerBedragenInp){
        if (beheerBedragenInp.getOffset().equals(0)){
            beheerBedragenReportLst.setShowPrevious(false);
        } else {
            beheerBedragenReportLst.setShowPrevious(true);
        }
        
        if (beheerBedragenReportLst.getBeheerBedragenReport() != null && beheerBedragenReportLst.getBeheerBedragenReport().size() == 10){
            beheerBedragenReportLst.setShowNext(true);
        } else {
            beheerBedragenReportLst.setShowNext(false);
        }
        
        return beheerBedragenReportLst;
    }
    
    @Override
    public List<String> findAllMonthsBedragen(List<Bedragen> bedragen){
        SimpleDateFormat dt = new SimpleDateFormat("MM/yyyy"); 
        List<String> months = new ArrayList<String>();
        
        for (Bedragen bedrag: bedragen){
            if (!months.contains(dt.format(bedrag.getDatum()))){
                months.add(dt.format(bedrag.getDatum()));
            }
        }
        
        return months;
    }
    
    @Override
    public Map<Integer, BigDecimal> findMainBedragen(List<Bedragen> bedragen, String Month){
        SimpleDateFormat dt = new SimpleDateFormat("MM/yyyy"); 
        Map<GroepenSrvPojo, BigDecimal> mainBedragenPerGroup = new HashMap<GroepenSrvPojo, BigDecimal>();
        
        for (Bedragen bedrag: bedragen){
            if (dt.format(bedrag.getDatum()).equals(Month)){
                GroepenSrvPojo mainGroup = new GroepenSrvPojo(GroepenSrvImpl.getHoofdGroep(bedrag.getGroep()));
                if (mainBedragenPerGroup.containsKey(mainGroup)){
                    BigDecimal ammount = mainBedragenPerGroup.get(mainGroup);
                    if (bedrag.getGroep().getNegatief().equals(1)){
                        ammount = ammount.add(bedrag.getBedrag());
                    } else {
                        ammount = ammount.subtract(bedrag.getBedrag());
                    }
                    mainBedragenPerGroup.put(mainGroup, ammount);
                } else {
                    if (bedrag.getGroep().getNegatief().equals(1)){
                        mainBedragenPerGroup.put(mainGroup, bedrag.getBedrag());
                    } else {
                        mainBedragenPerGroup.put(mainGroup, bedrag.getBedrag().multiply(new BigDecimal(-1)));
                    }
                }
            }
        }
        
        BigDecimal average = new BigDecimal(0);
        for (Map.Entry bedrag: mainBedragenPerGroup.entrySet()){
            average = average.add((BigDecimal) bedrag.getValue());
        }
        average = average.divide(new BigDecimal(mainBedragenPerGroup.size()), 2, RoundingMode.HALF_UP);
        
        Map<Integer, BigDecimal> result = new HashMap<Integer, BigDecimal>();
        
        result.put(0, average);
        
        for (Map.Entry bedrag: mainBedragenPerGroup.entrySet()){
            result.put(((GroepenSrvPojo) bedrag.getKey()).getPk_id(), (BigDecimal) bedrag.getValue());
        }
        
        return result;
    }
        
    @Override
    @Transactional
    public List<Bedragen> selectBedragenInPeriode(Date beginDate, Date endDate){
        List<Bedragen> bedragen = bedragenDao.BedragInPeriode(beginDate, endDate, null, false);
        
        return bedragen;
    }
    
    @Override
    public List<Bedragen> filterBedragenWithMainGroup(List<Bedragen> bedragen, List<Integer> MainGroupId){
        List<Bedragen> result = new ArrayList<Bedragen>();
        
        for (Bedragen bedrag: bedragen){
            if (MainGroupId.contains(GroepenSrvImpl.getHoofdGroep(bedrag.getGroep()).getPk_id())){
                result.add(bedrag);
            }
        }
        
        return result;
    }
}
