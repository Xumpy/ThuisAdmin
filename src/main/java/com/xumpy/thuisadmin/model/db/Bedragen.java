/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.model.db;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Nico
 */
@Entity
@Table(name="TA_BEDRAGEN")
public class Bedragen {
    @Id
    @Column(name="PK_ID")
    private Integer pk_id;
    
    @ManyToOne
    @JoinColumn(name="FK_TYPE_GROEP_ID")
    private Groepen groep;
    
    @ManyToOne
    @JoinColumn(name="FK_PERSOON_ID")
    private Personen persoon;
    
    @ManyToOne
    @JoinColumn(name="FK_REKENING_ID")
    private Rekeningen rekening;
    
    @Column(name="BEDRAG")
    private BigDecimal bedrag;
    
    @Column(name="DATUM")
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
}
