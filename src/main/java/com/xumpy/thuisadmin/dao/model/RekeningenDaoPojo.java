/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao.model;

import com.xumpy.thuisadmin.model.Rekeningen;
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
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Nico
 */
@Entity
@Table(name="TA_REKENINGEN")
public class RekeningenDaoPojo implements Serializable, Rekeningen {
    @Id
    @Column(name="PK_ID")
    private Integer pk_id;
    
    @Column(name="WAARDE")
    @NotNull
    private BigDecimal waarde;
    
    @Column(name="NAAM")
    @NotEmpty
    private String naam;
    
    @Column(name="LAATST_BIJGEWERKT")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date laatst_bijgewerkt;

    @ManyToOne
    @JoinColumn(name="FK_PERSONEN_ID")
    @NotNull
    private PersonenDaoPojo persoon;
    
    @Override
    public Integer getPk_id() {
        return pk_id;
    }

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    @Override
    public BigDecimal getWaarde() {
        return waarde;
    }

    public void setWaarde(BigDecimal waarde) {
        this.waarde = waarde;
    }

    @Override
    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    @Override
    public Date getLaatst_bijgewerkt() {
        return laatst_bijgewerkt;
    }

    public void setLaatst_bijgewerkt(Date laatst_bijgewerkt) {
        this.laatst_bijgewerkt = laatst_bijgewerkt;
    }

    @Override
    public PersonenDaoPojo getPersoon() {
        return persoon;
    }

    public void setPersoon(PersonenDaoPojo persoon) {
        this.persoon = persoon;
    }
    
    public RekeningenDaoPojo(){
        
    }
    public RekeningenDaoPojo(Rekeningen rekeningen){
        this.laatst_bijgewerkt = rekeningen.getLaatst_bijgewerkt();
        this.naam = rekeningen.getNaam();
        this.persoon = rekeningen.getPersoon()!=null ? new PersonenDaoPojo(rekeningen.getPersoon()): null;
        this.pk_id = rekeningen.getPk_id();
        this.waarde = rekeningen.getWaarde();
    }
}
