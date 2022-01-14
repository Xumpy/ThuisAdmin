/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services.implementations;

import com.xumpy.documenprovider.dao.implementations.DocumentProviderValidImpl;
import com.xumpy.documenprovider.domain.DocumentProviderValid;
import com.xumpy.finances.services.AccountService;
import com.xumpy.security.model.InvoiceType;
import com.xumpy.security.model.UserInfo;
import com.xumpy.thuisadmin.controllers.model.BeheerBedragenInp;
import com.xumpy.thuisadmin.controllers.model.BeheerBedragenReport;
import com.xumpy.thuisadmin.dao.implementations.BedragenDaoImpl;
import com.xumpy.thuisadmin.dao.implementations.DocumentenDaoImpl;
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
import com.xumpy.thuisadmin.dao.model.RekeningenDaoPojo;
import com.xumpy.thuisadmin.domain.Bedragen;
import com.xumpy.thuisadmin.domain.Documenten;
import com.xumpy.thuisadmin.domain.Groepen;
import com.xumpy.thuisadmin.domain.Rekeningen;
import com.xumpy.thuisadmin.services.BedragenSrv;
import com.xumpy.thuisadmin.services.model.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;

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

    @Autowired RekeningenSrvImpl rekeningenSrv;

    @Autowired
    private UserInfo userInfo;

    @Autowired DocumentenDaoImpl documentenDao;

    @Autowired DocumentProviderValidImpl documentProviderValid;
    @Autowired AccountService accountService;

    static final Logger Log = Logger.getLogger(BedragenSrvImpl.class.getName());

    public static final String NEGATIEF = "NEGATIEF";
    public static final String POSITIEF = "POSITIEF";
    public static final String UPDATE = "UPDATE";
    public static final String INSERT = "INSERT";
    public static final String DELETE = "DELETE";

    private Boolean isBedragValid(BedragenDaoPojo bedrag){
        return rekeningenSrv.isRekeningValid(bedrag.getRekening());
    }

    private List<BedragenDaoPojo> filterBedragen(List<BedragenDaoPojo> lstBedragen){
        List<BedragenDaoPojo> lstBedragenDaoPojo = new ArrayList<>();

        for(BedragenDaoPojo bedragen : lstBedragen){
            if (isBedragValid(bedragen) && bedragen.getProcessed() != null && bedragen.getProcessed()) lstBedragenDaoPojo.add(bedragen);
        }

        return lstBedragenDaoPojo;
    }

    @Transactional
    @Override
    public BigDecimal getCourantValue(){
        BigDecimal allNagitveAmounts = bedragenDao.allCourantNagitveAmounts().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal allPositiveAmounts = bedragenDao.allCourantPositiveAmounts().stream().reduce(BigDecimal.ZERO, BigDecimal::add);

        return allPositiveAmounts.subtract(allNagitveAmounts);
    }

    @Override
    @Transactional
    public Bedragen save(NieuwBedrag nieuwBedrag) {
        nieuwBedrag.setPersoon(new PersonenSrvPojo(userInfo.getPersoon()));
        BedragenDaoPojo bedragen = new BedragenDaoPojo(convertNieuwBedrag(nieuwBedrag));
        
        if (bedragen.getPk_id() == null){
            bedragen.setPk_id(bedragenDao.getNewPkId());
            
            bedragen = new BedragenDaoPojo(processRekeningBedrag(bedragen, INSERT));
        } else {
            bedragen = new BedragenDaoPojo(processRekeningBedrag(bedragen, UPDATE));
        }
        rekeningenDao.save(new RekeningenDaoPojo(bedragen.getRekening()));
        bedragenDao.save(bedragen);
        
        return bedragen;
    }

    @Override
    @Transactional
    public Bedragen delete(NieuwBedrag nieuwBedrag) {
        nieuwBedrag.setPersoon(new PersonenSrvPojo(userInfo.getPersoon()));
        Bedragen bedragen = convertNieuwBedrag(nieuwBedrag);
        bedragen = processRekeningBedrag(bedragen, DELETE);
        
        Rekeningen rekening = bedragen.getRekening();

        rekeningenDao.save(new RekeningenDaoPojo(rekening));
        bedragenDao.delete(new BedragenDaoPojo(bedragen));
        
        return bedragen;
    }

    @Override
    @Transactional
    public List<RekeningOverzicht> graphiekBedrag(Rekeningen rekening, Date beginDate, Date eindDate) {
        List<? extends Bedragen> lstBedragenSrv;
        if (rekening == null){
            lstBedragenSrv = filterBedragen(bedragenDao.BedragInPeriode(beginDate, eindDate, null, 0, userInfo.getPersoon().getPk_id()));
        } else {
            lstBedragenSrv = filterBedragen(bedragenDao.BedragInPeriode(beginDate, eindDate, rekening.getPk_id(), 0, userInfo.getPersoon().getPk_id()));
        }
        
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
    public FinanceOverzichtGroep graphiekOverzichtGroep(Date beginDate, Date eindDate, Integer showBedragPublicGroep) {
        List<? extends Bedragen> bedragInPeriode = filterBedragen(bedragenDao.BedragInPeriode(beginDate, eindDate, null, showBedragPublicGroep, userInfo.getPersoon().getPk_id()));
        FinanceOverzichtGroep financeOverzichtGroep = new FinanceOverzichtGroep();
        
        List<? extends Bedragen> lstBedragen = bedragInPeriode;
        
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
                overzichtGroep.setTotaal_kosten(bedragen.get(NEGATIEF).doubleValue());
                overzichtGroep.setTotaal_opbrengsten(bedragen.get(POSITIEF).doubleValue());

                overzichtGroepen.add(overzichtGroep);

                totaalKosten = totaalKosten.add(bedragen.get(NEGATIEF));
                totaalOpbrengsten = totaalOpbrengsten.add(bedragen.get(POSITIEF));
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
                                                                     Integer showBedragPublicGroep,
                                                                     OverzichtGroepBedragenTotal overzichtGroepBedragenTotal) {
        List<? extends Bedragen> lstBedragenInPeriode = filterBedragen(bedragenDao.BedragInPeriode(beginDate, eindDate, null, showBedragPublicGroep, userInfo.getPersoon().getPk_id()));
        
        Groepen groepenSrv = groepenDao.findById(typeGroepId).get();

        List<? extends Bedragen> lstBedragenNeg = getBedragenInGroep(lstBedragenInPeriode, groepenSrv, 1);
        List<? extends Bedragen> lstBedragenPos = getBedragenInGroep(lstBedragenInPeriode, groepenSrv, 0);

        List<OverzichtGroepBedragen> overzichtGroepBedragen = new ArrayList<OverzichtGroepBedragen>();
        
        BigDecimal somOverzicht = new BigDecimal(0);
        
        for(Bedragen bedrag: lstBedragenNeg){
            OverzichtGroepBedragen overzichtGroepBedrag = new OverzichtGroepBedragen();
            
            BedragenSrvPojo bedragSrvPojo = new BedragenSrvPojo(bedrag);
            bedragSrvPojo.setBedrag(bedrag.getBedrag().multiply(new BigDecimal(-1)));
            overzichtGroepBedrag.setWithBedrag(bedragSrvPojo);
            
            overzichtGroepBedragen.add(overzichtGroepBedrag);
            somOverzicht = somOverzicht.add(bedragSrvPojo.getBedrag());
        }
        
        for(Bedragen bedrag: lstBedragenPos){
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
                                                                     Integer showBedragPublicGroep,
                                                                     OverzichtGroepBedragenTotal overzichtGroepBedragenTotal) {
        List<? extends Bedragen> lstBedragenInPeriode = filterBedragen(bedragenDao.BedragInPeriode(beginDate, eindDate, null, showBedragPublicGroep, userInfo.getPersoon().getPk_id()));
        
        Groepen groepenSrv = groepenDao.findById(typeGroepId).get();
        
        Integer negatief = new Integer(0);
        
        if (typeGroepKostOpbrengst.equals(1)){
            negatief = 0;
        } else {
            negatief = 1;
        }
        List<? extends Bedragen> lstBedragen = getBedragenInGroep(lstBedragenInPeriode, groepenSrv, negatief);
        
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
    public BeheerBedragenReportLst reportBedragen(BeheerBedragenReportLst beheerBedragenReportLst, Integer offset, Rekeningen rekening, String searchText, Boolean validAccountyBedrag) {
        
        searchText = StringUtils.isEmpty(searchText) ? null : "%" + searchText + "%";
        
        List<BeheerBedragenReport> beheerBedragenReport = new ArrayList<BeheerBedragenReport>();
        
        Pageable topTen = PageRequest.of(offset, 10);
        
        Integer rekeningId;
        if (rekening == null){
            rekeningId = null;
        } else {
            rekeningId = rekening.getPk_id();
        }

        if (!validAccountyBedrag){
            for (Bedragen bedrag: bedragenDao.reportBedragen(rekeningId, searchText, userInfo.getPersoon().getPk_id(),
                    userInfo.getInvoiceType().equals(InvoiceType.PROFESSIONAL) ? Boolean.TRUE :
                            userInfo.getInvoiceType().equals(InvoiceType.PERSONAL) ? Boolean.FALSE : null, topTen).getContent()){
                beheerBedragenReport.add(new BeheerBedragenReport(bedrag, isAccountancyBedragValid(bedrag)));
            }

        } else {
            for (Bedragen bedrag: bedragenDao.reportInValidAccountantBedragen(rekeningId, searchText, userInfo.getPersoon().getPk_id(),
                    userInfo.getInvoiceType().equals(InvoiceType.PROFESSIONAL) ? Boolean.TRUE :
                            userInfo.getInvoiceType().equals(InvoiceType.PERSONAL) ? Boolean.FALSE : null, topTen).getContent()){
                beheerBedragenReport.add(new BeheerBedragenReport(bedrag, isAccountancyBedragValid(bedrag)));
            }
        }

        beheerBedragenReportLst.setBeheerBedragenReport(beheerBedragenReport);
        
        return beheerBedragenReportLst;
    }

    @Override
    @Transactional(value="transactionManager")
    public Bedragen findBedrag(Integer bedragId) {
        return bedragenDao.findById(bedragId).get();
    }
    
    public Map OverviewRekeningData(List<? extends Bedragen> bedragen){
        Map overviewRekeningData = new LinkedHashMap();

        if (bedragen.size() != 0) {
            BigDecimal rekeningStand;
            if (isRekeningUnique(bedragen)) {
                rekeningStand = getBedragAtDate(bedragen.get(0).getDatum(), bedragen.get(0).getRekening());
            } else {
                rekeningStand = getBedragAtDate(bedragen.get(0).getDatum(), null);
            }
            overviewRekeningData.put(bedragen.get(0).getDatum(), rekeningStand);

            for (Integer i = 1; i < bedragen.size(); i++) {
                if (!bedragen.get(i).getDatum().equals(bedragen.get(0).getDatum())) {
                    if (bedragen.get(i).getGroep().getNegatief().equals(1)) {
                        rekeningStand = rekeningStand.subtract(bedragen.get(i).getBedrag());
                    }
                    if (bedragen.get(i).getGroep().getNegatief().equals(0)) {
                        rekeningStand = rekeningStand.add(bedragen.get(i).getBedrag());
                    }
                    overviewRekeningData.put(bedragen.get(i).getDatum(), rekeningStand);
                }
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
    public List<? extends Bedragen> orderByGroup(List<? extends Bedragen> bedragen){
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
    
    public BigDecimal getTotalRekeningBedragen(List<? extends Bedragen> bedragen){
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
    
    public boolean isRekeningUnique(List<? extends Bedragen> bedragen){
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
    
    public Map<Groepen, Map<String, BigDecimal>> OverviewRekeningGroep(List<? extends Bedragen> bedragen){
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
    
    public List<? extends Bedragen> getBedragenInGroep(List<? extends Bedragen> bedragen, Groepen hoofdGroep, Integer negatief){
        List<Bedragen> bedragenInGroep = new ArrayList<Bedragen>();
        
        for(Bedragen bedrag: bedragen){
            if (GroepenSrvImpl.getHoofdGroep(bedrag.getGroep()).getPk_id().equals(hoofdGroep.getPk_id())  && bedrag.getGroep().getNegatief().equals(negatief)){
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
        bedragen.setTaxPercentagePaid(nieuwBedrag.getTaxPercentagePaid());
        bedragen.setWeightAccountancy(nieuwBedrag.getWeightAccountancy());
        BigDecimal bigDecimalBedrag = convertComma(nieuwBedrag.getBedrag());
        bedragen.setBedrag(bigDecimalBedrag);
        bedragen.setInvoice(nieuwBedrag.getInvoice() != null ? new InvoicesSrvPojo(nieuwBedrag.getInvoice()) : null);
        bedragen.setProcessed(nieuwBedrag.getProcessed());
        bedragen.setManagedByAccountant(nieuwBedrag.getManagedByAccountant());
        bedragen.setCourant(nieuwBedrag.getCourant());

        return bedragen;
    }

    private RekeningenSrvPojo updateBedrag(BedragenSrvPojo oldBedrag, BedragenSrvPojo currentBedrag){
        RekeningenSrvPojo rekening = new RekeningenSrvPojo(currentBedrag.getRekening());

        if (!oldBedrag.getRekening().equals(currentBedrag.getRekening())){
            rekening = new RekeningenSrvPojo(moveBedragToRekening(oldBedrag, rekening));
        }

        if (currentBedrag.getGroep().getNegatief().equals(1)){
            if (oldBedrag.getGroep().getNegatief().equals(1)){
                rekening.setWaarde((rekening.getWaarde().add(oldBedrag.getBedrag())));
            } else {
                rekening.setWaarde((rekening.getWaarde().subtract(oldBedrag.getBedrag())));
            }
            rekening.setWaarde((rekening.getWaarde().subtract(currentBedrag.getBedrag())));
        } else {
            if (oldBedrag.getGroep().getNegatief().equals(1)){
                rekening.setWaarde((rekening.getWaarde().add(oldBedrag.getBedrag())));
            } else {
                rekening.setWaarde((rekening.getWaarde().subtract(oldBedrag.getBedrag())));
            }
            rekening.setWaarde((rekening.getWaarde().add(currentBedrag.getBedrag())));
        }

        return rekening;
    }

    private RekeningenSrvPojo insertBedrag(BedragenSrvPojo currentBedrag){
        RekeningenSrvPojo rekening = new RekeningenSrvPojo(currentBedrag.getRekening());
        if (currentBedrag.getGroep().getNegatief().equals(1)){
            rekening.setWaarde(rekening.getWaarde().subtract(currentBedrag.getBedrag()));
        } else {
            rekening.setWaarde(rekening.getWaarde().add(currentBedrag.getBedrag()));
        }
        return rekening;
    }

    private RekeningenSrvPojo deleteBedrag(BedragenSrvPojo currentBedrag){
        RekeningenSrvPojo rekening = new RekeningenSrvPojo(currentBedrag.getRekening());
        if (currentBedrag.getGroep().getNegatief().equals(1)){
            rekening.setWaarde((rekening.getWaarde().add(currentBedrag.getBedrag())));
        } else {
            rekening.setWaarde((rekening.getWaarde().subtract(currentBedrag.getBedrag())));
        }
        return rekening;
    }

    public Bedragen processRekeningBedrag(Bedragen bedrag, String transaction){
        BedragenSrvPojo bedragenSrvPojo = new BedragenSrvPojo(bedrag);

        if (transaction.equals(INSERT)){
            if (bedrag.getProcessed() != null && bedrag.getProcessed()){
                bedragenSrvPojo.setRekening(insertBedrag(bedragenSrvPojo));
            }
        } 
        
        if (transaction.equals(UPDATE)){
            BedragenSrvPojo oldBedrag = new BedragenSrvPojo(bedragenDao.findById(bedragenSrvPojo.getPk_id()).get());
            if (oldBedrag.getProcessed() != null && oldBedrag.getProcessed()){
                bedragenSrvPojo.setProcessed(true);
                bedragenSrvPojo.setRekening(updateBedrag(oldBedrag, bedragenSrvPojo));
            } else {
                if (bedragenSrvPojo.getProcessed() != null && bedragenSrvPojo.getProcessed()){
                    bedragenSrvPojo.setRekening(insertBedrag(bedragenSrvPojo));
                }
            }
        }

        if (transaction.equals(DELETE)){
            BedragenSrvPojo oldBedrag = new BedragenSrvPojo(bedragenDao.findById(bedragenSrvPojo.getPk_id()).get());
            if (oldBedrag.getProcessed() != null && oldBedrag.getProcessed()){
                bedragenSrvPojo.setRekening(deleteBedrag(bedragenSrvPojo));
            }
        }

        return bedragenSrvPojo;
    }

    public Rekeningen moveBedragToRekening(BedragenSrvPojo bedrag, Rekeningen rekening2) {
        RekeningenSrvPojo rekening2SrvPojo = new RekeningenSrvPojo(rekening2);
        
        if (bedrag.getGroep().getNegatief().equals(0)){
            bedrag.getRekening().setWaarde(bedrag.getRekening().getWaarde().subtract(bedrag.getBedrag()));
            rekeningenDao.save(new RekeningenDaoPojo(bedrag.getRekening()));
            rekening2 = rekeningenDao.findById(rekening2.getPk_id()).get();
            rekening2SrvPojo.setWaarde(rekening2.getWaarde().add(bedrag.getBedrag()));
        } else {
            bedrag.getRekening().setWaarde(bedrag.getRekening().getWaarde().add(bedrag.getBedrag()));
            rekeningenDao.save(new RekeningenDaoPojo(bedrag.getRekening()));
            rekening2 = rekeningenDao.findById(rekening2.getPk_id()).get();
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
    public List<String> findAllMonthsBedragen(List<? extends Bedragen> bedragen){
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
    public Map<Integer, BigDecimal> findMainBedragen(List<? extends Bedragen> bedragen, String Month){
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
    public List<? extends Bedragen> selectBedragenInPeriode(Date beginDate, Date endDate){
        List<? extends Bedragen> bedragen = filterBedragen(bedragenDao.BedragInPeriode(beginDate, endDate, null, 0, userInfo.getPersoon().getPk_id()));
        
        return bedragen;
    }
    
    @Override
    public List<? extends Bedragen> filterBedragenWithMainGroup(List<? extends Bedragen> bedragen, List<Integer> MainGroupId){
        List<Bedragen> result = new ArrayList<Bedragen>();
        
        for (Bedragen bedrag: bedragen){
            if (MainGroupId.contains(GroepenSrvImpl.getHoofdGroep(bedrag.getGroep()).getPk_id())){
                result.add(bedrag);
            }
        }
        
        return result;
    }
    
    @Override
    @Transactional
    public BigDecimal getBedragAtDate(Date date, Rekeningen rekening){
        BigDecimal rekeningStand;
        
        List<BedragenDaoPojo> lstBedragen;
        if (rekening != null){
            lstBedragen = filterBedragen(bedragenDao.getBedragenUntilDate(date, rekening.getPk_id(), userInfo.getPersoon().getPk_id()));
            rekeningStand = rekening.getWaarde();
        } else {
            lstBedragen = filterBedragen(bedragenDao.getBedragenUntilDate(date, null, userInfo.getPersoon().getPk_id()));
            rekeningStand = rekeningenDao.totalAllRekeningen(userInfo.getPersoon().getPk_id(),
                    userInfo.getInvoiceType().equals(InvoiceType.PROFESSIONAL) ? Boolean.TRUE :
                    userInfo.getInvoiceType().equals(InvoiceType.PERSONAL) ? Boolean.FALSE : null
            );
        }
        
        for (BedragenDaoPojo bedrag: lstBedragen){
            if (bedrag.getGroep().getNegatief().equals(1)){
                rekeningStand = rekeningStand.add(bedrag.getBedrag());
            }
            if (bedrag.getGroep().getNegatief().equals(0)){
                rekeningStand = rekeningStand.subtract(bedrag.getBedrag());
            }
        }
        
        return rekeningStand;
    }

    @Override
    @Transactional(value="jpaTransactionManager")
    public Map<String, BigDecimal> getPositiveNegativeBedragen(Date startDate, Date endDate){
        Map<String, BigDecimal> bedragen = new HashMap<String, BigDecimal>();

        List<BedragenDaoPojo> bedragenDaoPojos = filterBedragen(bedragenDao.BedragInPeriode(startDate, endDate, userInfo.getPersoon().getPk_id()));

        bedragen.put("POS", new BigDecimal(0));
        bedragen.put("NEG", new BigDecimal(0));

        for (BedragenDaoPojo bedragenDaoPojo: bedragenDaoPojos){
            if (bedragenDaoPojo.getGroep().getNegatief() == 1){
                bedragen.put("NEG", bedragen.get("NEG").add(bedragenDaoPojo.getBedrag()));
            } else {
                bedragen.put("POS", bedragen.get("POS").add(bedragenDaoPojo.getBedrag()));
            }
        }

        return bedragen;
    }

    public List<List<Object>> getBedragenInMonthRange(String startMonth, String endMonth) throws ParseException {
        List<List<Object>> bedragenInMonthRange = new ArrayList<List<Object>>();

        for(String month: getAllMonths(startMonth, endMonth)){
            SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
            Date startDate = dt.parse("01/" + month);
            Calendar c = Calendar.getInstance();
            c.setTime(startDate);
            c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
            Date endDate = c.getTime();

            Map<String, BigDecimal> bedragen = getPositiveNegativeBedragen(startDate, endDate);

            List<Object> values = new ArrayList<Object>();
            values.add(month);
            values.add(bedragen.get("POS"));
            values.add(bedragen.get("NEG"));

            bedragenInMonthRange.add(values);
        }

        return bedragenInMonthRange;
    }


    private Integer monthToInteger(String monthYear){
        String[] monthSplit = monthYear.split("/");
        Integer month = Integer.parseInt(monthSplit[0]);
        Integer year = Integer.parseInt(monthSplit[1]);

        return (year * 12) + month;
    }

    private String integerToMonth(Integer monthInteger){
        Integer month = monthInteger % 12;
        Integer year = (int) Math.floor(monthInteger / 12);

        if (month == 0){
            month = 12;
            year = year - 1;
        }

        return String.format("%02d", month) + "/" + Integer.toString(year);
    }

    public List<String> getAllMonths(String startMonth, String endMonth){
        List<String> allMonths = new ArrayList<String>();
        for(int i=monthToInteger(startMonth); i<=monthToInteger(endMonth); i++){
            allMonths.add(integerToMonth(i));
        }

        return allMonths;
    }

    private Boolean isDocumentSentToTheValidDocumentProviders(Documenten document, List<? extends DocumentProviderValid> validDocumentProviders){
        for(DocumentProviderValid validDocumentProvider: validDocumentProviders){
            if(!accountService.isDocumentSentToDocumentProvider(document, validDocumentProvider.getDocumentProvider())){
                return false;
            }
        }
        return true;
    }

    private Boolean isOneDocumentSentToTheValidDocumentProviders(List<? extends Documenten> documenten, List<? extends DocumentProviderValid> validDocumentProviders){
        for(Documenten document: documenten){
            if (isDocumentSentToTheValidDocumentProviders(document, validDocumentProviders)){
                return true;
            }
        }
        return false;
    }

    public Boolean isAccountancyBedragValid(Bedragen bedrag){
        if (bedrag.getRekening().getProfessional() != null && bedrag.getRekening().getProfessional()){
            if (bedrag.getCourant() != null && bedrag.getCourant()){
                return true;
            }
            if (bedrag.getManagedByAccountant() != null && bedrag.getManagedByAccountant()){
                return true;
            }
            List<? extends Documenten> documenten = documentenDao.fetchDocumentByBedrag(bedrag.getPk_id());

            if (documenten.size() > 0){
                List<? extends DocumentProviderValid> validDocumentProviders = documentProviderValid.findAllValidDocumentProviders(bedrag.getDatum());
                return isOneDocumentSentToTheValidDocumentProviders(documenten, validDocumentProviders);
            }
            return false;
        }
        return true;
    }
}
