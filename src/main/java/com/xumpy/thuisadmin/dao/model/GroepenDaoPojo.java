/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao.model;

import com.xumpy.thuisadmin.domain.Groepen;
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
public class GroepenDaoPojo implements Serializable, Groepen {
    @Id
    @Column(name="PK_ID")
    private Integer pk_id;
    
    @ManyToOne
    @JoinColumn(name="FK_HOOFD_TYPE_GROEP_ID")
    private GroepenDaoPojo hoofdGroep;
    
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
    private PersonenDaoPojo persoon;

    @Column(name="PUBLIC_GROEP")
    private Integer publicGroep;

    @Column(name="CLOSED")
    private Boolean closed;

    @Column(name="YUKI_CATEGORY")
    private Integer category;

    @Override
    public Integer getPk_id() {
        return pk_id;
    }

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    @Override
    public GroepenDaoPojo getHoofdGroep() {
        return hoofdGroep;
    }

    public void setHoofdGroep(GroepenDaoPojo hoofdGroep) {
        this.hoofdGroep = hoofdGroep;
    }

    @Override
    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    @Override
    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    @Override
    public Integer getNegatief() {
        return negatief;
    }

    public void setNegatief(Integer negatief) {
        this.negatief = negatief;
    }

    @Override
    public PersonenDaoPojo getPersoon() {
        return persoon;
    }

    public void setPersoon(PersonenDaoPojo persoon) {
        this.persoon = persoon;
    }

    @Override
    public Integer getPublicGroep() {
        return publicGroep;
    }

    public void setPublicGroep(Integer publicGroep) {
        this.publicGroep = publicGroep;
    }
    
    public GroepenDaoPojo(){
        
    }

    @Override
    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    @Override
    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public GroepenDaoPojo(Groepen groepen){
        this.hoofdGroep = groepen.getHoofdGroep()!=null ? new GroepenDaoPojo(groepen.getHoofdGroep()) : null;
        this.naam = groepen.getNaam();
        this.negatief = groepen.getNegatief();
        this.omschrijving = groepen.getOmschrijving();
        this.persoon = groepen.getPersoon()!=null ? new PersonenDaoPojo(groepen.getPersoon()) : null;
        this.pk_id = groepen.getPk_id();
        this.publicGroep = groepen.getPublicGroep();
        this.closed = groepen.getClosed();
        this.category = groepen.getCategory();
    }
}
