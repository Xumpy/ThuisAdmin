/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao.model;

import com.xumpy.thuisadmin.model.Bedragen;
import java.io.Serializable;
import java.math.BigDecimal;
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
public class BedragenDaoPojo implements Serializable, Bedragen{
    @Id
    @Column(name="PK_ID")
    private Integer pk_id;
    
    @ManyToOne
    @JoinColumn(name="FK_TYPE_GROEP_ID")
    @NotNull
    private GroepenDaoPojo groep;
    
    @ManyToOne
    @JoinColumn(name="FK_PERSOON_ID")
    @NotNull
    private PersonenDaoPojo persoon;
    
    @ManyToOne
    @JoinColumn(name="FK_REKENING_ID")
    @NotNull
    private RekeningenDaoPojo rekening;
    
    @Column(name="BEDRAG")
    @NotNull
    private BigDecimal bedrag;
    
    @Column(name="DATUM")
    @NotNull
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datum;
    
    @Column(name="OMSCHRIJVING")
    private String omschrijving;

    @Override
    public Integer getPk_id() {
        return pk_id;
    }

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    @Override
    public RekeningenDaoPojo getRekening() {
        return rekening;
    }

    public void setRekening(RekeningenDaoPojo rekening) {
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
    public GroepenDaoPojo getGroep() {
        return groep;
    }

    public void setGroep(GroepenDaoPojo groep) {
        this.groep = groep;
    }

    @Override
    public PersonenDaoPojo getPersoon() {
        return persoon;
    }

    public void setPersoon(PersonenDaoPojo persoon) {
        this.persoon = persoon;
    }
    
    public BedragenDaoPojo(){
        
    }
    
    public BedragenDaoPojo(Bedragen bedragen){
        this.bedrag = bedragen.getBedrag();
        this.datum = bedragen.getDatum();
        this.groep = bedragen.getGroep()!=null ? new GroepenDaoPojo(bedragen.getGroep()): null;
        this.omschrijving = bedragen.getOmschrijving();
        this.persoon = bedragen.getPersoon()!=null ? new PersonenDaoPojo(bedragen.getPersoon()) : null;
        this.pk_id = bedragen.getPk_id();
        this.rekening = bedragen.getRekening()!=null ? new RekeningenDaoPojo(bedragen.getRekening()) : null;
    }
}
