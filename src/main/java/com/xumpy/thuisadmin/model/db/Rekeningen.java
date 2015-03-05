/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.model.db;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.CascadeType;
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
public class Rekeningen implements Serializable {
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
    private Personen persoon;
    
    public Integer getPk_id() {
        return pk_id;
    }

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    public BigDecimal getWaarde() {
        return waarde;
    }

    public void setWaarde(BigDecimal waarde) {
        this.waarde = waarde;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Date getLaatst_bijgewerkt() {
        return laatst_bijgewerkt;
    }

    public void setLaatst_bijgewerkt(Date laatst_bijgewerkt) {
        this.laatst_bijgewerkt = laatst_bijgewerkt;
    }

    public Personen getPersoon() {
        return persoon;
    }

    public void setPersoon(Personen persoon) {
        this.persoon = persoon;
    }
}
