/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.model.db;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Nico
 */
@Entity
@Table(name="TA_BEDRAGEN")
public class Bedragen implements Serializable, Comparable<Bedragen> {
    @Id
    @Column(name="PK_ID")
    private Integer pk_id;
    
    @ManyToOne
    @JoinColumn(name="FK_TYPE_GROEP_ID")
    @NotNull
    private Groepen groep;
    
    @ManyToOne
    @JoinColumn(name="FK_PERSOON_ID")
    @NotNull
    private Personen persoon;
    
    @ManyToOne
    @JoinColumn(name="FK_REKENING_ID")
    @NotNull
    private Rekeningen rekening;
    
    @Column(name="BEDRAG")
    @NotNull
    private BigDecimal bedrag;
    
    @Column(name="DATUM")
    @NotNull
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datum;
    
    @Column(name="OMSCHRIJVING")
    private String omschrijving;

    public Integer getPk_id() {
        return pk_id;
    }

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    public Rekeningen getRekening() {
        return rekening;
    }

    public void setRekening(Rekeningen rekening) {
        this.rekening = rekening;
    }

    public BigDecimal getBedrag() {
        return bedrag;
    }

    public void setBedrag(BigDecimal bedrag) {
        this.bedrag = bedrag;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Groepen getGroep() {
        return groep;
    }

    public void setGroep(Groepen groep) {
        this.groep = groep;
    }

    public Personen getPersoon() {
        return persoon;
    }

    public void setPersoon(Personen persoon) {
        this.persoon = persoon;
    }
    
    @Override
    public boolean equals(Object object) {
        try{
            Bedragen compareBedrag = (Bedragen)object;
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
    public int compareTo(Bedragen compBedrag) {
        Long daysBetweenDate = compBedrag.getDatum().getTime() - this.datum.getTime();
        
        return daysBetweenDate.intValue();
    }
    
    public static Comparator<Bedragen> bedragComparator = new Comparator<Bedragen>() {
        @Override
        public int compare(Bedragen bedrag1, Bedragen bedrag2) {
            return bedrag1.compareTo(bedrag2);
        }
    };
}
