/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services;

import com.xumpy.thuisadmin.dao.BedragenDaoImpl;
import com.xumpy.thuisadmin.dao.RekeningenDaoImpl;
import com.xumpy.thuisadmin.model.db.Bedragen;
import com.xumpy.thuisadmin.model.db.Rekeningen;
import com.xumpy.thuisadmin.model.view.BeheerBedragenReport;
import com.xumpy.thuisadmin.model.view.FinanceOverzichtGroep;
import com.xumpy.thuisadmin.model.view.NieuwBedrag;
import com.xumpy.thuisadmin.model.view.OverzichtGroep;
import com.xumpy.thuisadmin.model.view.OverzichtGroepBedragen;
import com.xumpy.thuisadmin.model.view.OverzichtGroepBedragenTotal;
import com.xumpy.thuisadmin.model.view.RekeningOverzicht;
import com.xumpy.thuisadmin.model.view.graphiek.OverzichtBedrag;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
public class BedragenSrvImpl implements BedragenSrv{

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
            
            Rekeningen rekening = bedragen.getRekening();
            rekening.setWaarde((rekening.getWaarde().subtract(bedragen.getBedrag())));
            
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
        BigDecimal oldBedrag = bedragenDao.getBedrag(bedragen.getPk_id());
        
        Rekeningen rekening = bedragen.getRekening();
        rekening.setWaarde((rekening.getWaarde().add(oldBedrag)));
        rekening.setWaarde((rekening.getWaarde().subtract(bedragen.getBedrag())));
        
        bedragenDao.update(bedragen);
        rekeningenDao.update(rekening);
    }

    @Override
    @Transactional(readOnly=false)
    public void delete(NieuwBedrag nieuwBedrag) {
        Bedragen bedragen = convertNieuwBedrag(nieuwBedrag);
        
        Rekeningen rekening = bedragen.getRekening();
        rekening.setWaarde((rekening.getWaarde().add(bedragen.getBedrag())));
        
        bedragenDao.delete(bedragen);
        rekeningenDao.update(rekening);
    }

    @Override
    @Transactional
    public List<RekeningOverzicht> graphiekBedrag(Rekeningen rekening, Date beginDate, Date eindDate) {
        //return bedragenDao.graphiekBedrag(rekening, beginDate, eindDate);
        return overzichtBedragen(rekening, beginDate, eindDate);
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
    
    private List<RekeningOverzicht> overzichtBedragen(Rekeningen rekening,
                                                      Date beginDate,
                                                      Date eindDate){
        List<RekeningOverzicht> rekeningOverzichten = new ArrayList<RekeningOverzicht>();
        List<OverzichtBedrag> overzichtBedrag = new ArrayList<OverzichtBedrag>();
        BigDecimal totaalRekening = new BigDecimal(0);
        
        if (rekening == null){
            overzichtBedrag = bedragenDao.findAllBedragen(beginDate, eindDate);
            List<Rekeningen> rekeningen = rekeningenDao.findAllRekeningen();
            
            for (Rekeningen reken: rekeningen){
                totaalRekening = totaalRekening.add(reken.getWaarde());
            }
            totaalRekening = totaalRekening.subtract(bedragenDao.somBedragDatum(beginDate));
        } else {
            overzichtBedrag = bedragenDao.findBedragenRekening(rekening, beginDate, eindDate);
            totaalRekening = rekening.getWaarde();
            totaalRekening = totaalRekening.subtract(bedragenDao.somBedragDatum(rekening, beginDate));
        }
        
        Date old_date = overzichtBedrag.get(0).getDatum();
        RekeningOverzicht rekeningOverzicht = new RekeningOverzicht();
        for (int i = 0; i<overzichtBedrag.size(); i++){
            OverzichtBedrag bedrag = overzichtBedrag.get(i);
            
            if (bedrag.getDatum().equals(old_date)){
                totaalRekening = totaalRekening.add(bedrag.getBedrag());
                if (i == (overzichtBedrag.size()-1)){
                    rekeningOverzicht.setDatum(bedrag.getDatum());
                    rekeningOverzicht.setBedrag(totaalRekening);
                    rekeningOverzichten.add(rekeningOverzicht);
                }
            } else {
                rekeningOverzicht.setDatum(old_date);
                rekeningOverzicht.setBedrag(totaalRekening);
                rekeningOverzichten.add(rekeningOverzicht);
                totaalRekening = totaalRekening.add(bedrag.getBedrag());
                rekeningOverzicht = new RekeningOverzicht();
                if (i == (overzichtBedrag.size() -1)){
                    rekeningOverzicht.setDatum(bedrag.getDatum());
                    rekeningOverzicht.setBedrag(totaalRekening);
                    rekeningOverzichten.add(rekeningOverzicht);
                }
            }
            old_date = bedrag.getDatum();
            rekeningOverzicht.setDatum(bedrag.getDatum());
        }
        
        return rekeningOverzichten;
    }
    
    public Bedragen convertNieuwBedrag(NieuwBedrag nieuwBedrag){
        Bedragen bedragen = new Bedragen();
        
        bedragen.setPk_id(nieuwBedrag.getPk_id());
        bedragen.setDatum(nieuwBedrag.getDatum());
        bedragen.setGroep(nieuwBedrag.getGroep());
        bedragen.setOmschrijving(nieuwBedrag.getOmschrijving());
        bedragen.setPersoon(nieuwBedrag.getPersoon());
        bedragen.setRekening(nieuwBedrag.getRekening());
        
        String bedrag = nieuwBedrag.getBedrag();
        if (bedrag.contains(",")){
            bedrag = bedrag.replace(".", "");
            bedrag = bedrag.replace(",", ".");
        } else {
            if (nieuwBedrag.getBedrag().indexOf(".", nieuwBedrag.getBedrag().indexOf(".") + 1) != -1){
                bedrag = bedrag.replace(".", "");
            }
        }
        
        Log.log(Level.INFO, "Bedrag transformed: {0}", bedrag);
        
        NumberFormat nf = NumberFormat.getInstance(new Locale("US"));
        BigDecimal bigDecimalBedrag = new BigDecimal(0);
        try {
            bigDecimalBedrag = new BigDecimal(nf.parse(bedrag).doubleValue());
        } catch (ParseException ex) {
            Logger.getLogger(BedragenSrvImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        bedragen.setBedrag(bigDecimalBedrag);
        
        return bedragen;
    }
}
