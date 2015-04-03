/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services.logic;

import com.xumpy.thuisadmin.dao.implementations.BedragenDaoImpl;
import com.xumpy.thuisadmin.dao.implementations.RekeningenDaoImpl;
import com.xumpy.thuisadmin.dao.model.BedragenDaoPojo;
import com.xumpy.thuisadmin.dao.model.RekeningenDaoPojo;
import com.xumpy.thuisadmin.controllers.model.NieuwBedrag;
import com.xumpy.thuisadmin.model.Bedragen;
import com.xumpy.thuisadmin.model.Rekeningen;
import com.xumpy.thuisadmin.services.implementations.BedragenSrvImpl;
import com.xumpy.thuisadmin.services.model.BedragenSrvPojo;
import com.xumpy.thuisadmin.services.model.RekeningenSrvPojo;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nico
 */
public class BedragenLogic {
    
    @Autowired
    private BedragenDaoImpl bedragenDao;
    
    @Autowired
    private RekeningenDaoImpl rekeningenDao;
    
    public static final String UPDATE = "UPDATE";
    public static final String INSERT = "INSERT";
    public static final String DELETE = "DELETE";
    
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
}
