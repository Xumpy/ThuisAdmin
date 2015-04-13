/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services.model;

import com.xumpy.thuisadmin.dao.model.BedragenDaoPojo;
import com.xumpy.thuisadmin.domain.Bedragen;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;

/**
 *
 * @author nicom
 */
public class BedragenSrvPojo implements Comparable<BedragenSrvPojo>, Bedragen {
    private Integer pk_id;
    private GroepenSrvPojo groep;
    private PersonenSrvPojo persoon;
    private RekeningenSrvPojo rekening;
    private BigDecimal bedrag;
    private Date datum;
    private String omschrijving;

    @Override
    public Integer getPk_id() {
        return pk_id;
    }

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    @Override
    public GroepenSrvPojo getGroep() {
        return groep;
    }

    public void setGroep(GroepenSrvPojo groep) {
        this.groep = groep;
    }

    @Override
    public PersonenSrvPojo getPersoon() {
        return persoon;
    }

    public void setPersoon(PersonenSrvPojo persoon) {
        this.persoon = persoon;
    }

    @Override
    public RekeningenSrvPojo getRekening() {
        return rekening;
    }

    public void setRekening(RekeningenSrvPojo rekening) {
        this.rekening = rekening;
    }

    @Override
    public BigDecimal getBedrag() {
        return bedrag;
    }

    public void setBedrag(BigDecimal bedrag) {
        this.bedrag = bedrag;
    }

    @Override
    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    @Override
    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }
    
    @Override
    public boolean equals(Object object) {
        try{
            BedragenDaoPojo compareBedrag = (BedragenDaoPojo)object;
            if (!this.getBedrag().equals(compareBedrag.getBedrag())){
                return false;
            }
            if (!this.getDatum().equals(compareBedrag.getDatum())){
                return false;
            }
            if (!this.getGroep().equals(compareBedrag.getGroep())){
                return false;
            }
            if (!this.getOmschrijving().equals(compareBedrag.getOmschrijving())){
                return false;
            }
            if (!this.getPersoon().equals(compareBedrag.getPersoon())){
                return false;
            }
            if (!this.getRekening().equals(compareBedrag.getRekening())){
                return false;
            }
            return true;
        } catch (Exception ex){
            return false;
        }
    }

    @Override
    public int compareTo(BedragenSrvPojo compBedrag) {
        return this.datum.compareTo(compBedrag.getDatum());
    }
    
    public static Comparator<BedragenSrvPojo> bedragComparator = new Comparator<BedragenSrvPojo>() {
        @Override
        public int compare(BedragenSrvPojo bedrag1, BedragenSrvPojo bedrag2) {
            return bedrag1.compareTo(bedrag2);
        }
    };
    
    public BedragenSrvPojo(){
        
    }
    
    public BedragenSrvPojo(Bedragen bedragen){
        this.bedrag = bedragen.getBedrag();
        this.datum = bedragen.getDatum();
        this.groep = bedragen.getGroep()!=null ? new GroepenSrvPojo(bedragen.getGroep()):null;
        this.omschrijving = bedragen.getOmschrijving();
        this.persoon = bedragen.getPersoon()!=null ? new PersonenSrvPojo(bedragen.getPersoon()): null;
        this.pk_id = bedragen.getPk_id();
        this.rekening = bedragen.getRekening()!=null ? new RekeningenSrvPojo(bedragen.getRekening()): null;
    }
}
