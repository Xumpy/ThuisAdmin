/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.model.db;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Nico
 */
@Entity
@Table(name="TA_TYPE_GROEP")
//@GeneratedValue(strategy = )
public class Groepen implements Serializable {
    @Id
    @Column(name="PK_ID")
    private Integer pk_id;
    
    @ManyToOne
    @JoinColumn(name="FK_HOOFD_TYPE_GROEP_ID")
    private Groepen hoofdGroep;
    
    @Column(name="NAAM")
    @NotEmpty
    private String naam;
    
    @Column(name="OMSCHRIJVING")
    private String omschrijving;
    
    @Column(name="NEGATIEF")
    private Integer negatief;
    
    @ManyToOne
    @JoinColumn(name="FK_PERSONEN_ID")
    @NotNull
    private Personen persoon;

    @Column(name="CODE_ID")
    private String codeId;

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }
    
    public Integer getPk_id() {
        return pk_id;
    }

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    public Groepen getHoofdGroep() {
        return hoofdGroep;
    }

    public void setHoofdGroep(Groepen hoofdGroep) {
        this.hoofdGroep = hoofdGroep;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Integer getNegatief() {
        return negatief;
    }

    public void setNegatief(Integer negatief) {
        this.negatief = negatief;
    }

    public Personen getPersoon() {
        return persoon;
    }

    public void setPersoon(Personen persoon) {
        this.persoon = persoon;
    }
}
