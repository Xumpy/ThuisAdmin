/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services.model;

import com.xumpy.thuisadmin.domain.Groepen;

/**
 *
 * @author nicom
 */
public class GroepenSrvPojo implements Groepen{
    private Integer pk_id;
    private GroepenSrvPojo hoofdGroep;
    private String naam;
    private String omschrijving;
    private Integer negatief;
    private PersonenSrvPojo persoon;
    private Integer publicGroep;
    private Boolean closed;
    private Integer category;

    @Override
    public Integer getPk_id() {
        return pk_id;
    }

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    @Override
    public GroepenSrvPojo getHoofdGroep() {
        return hoofdGroep;
    }

    public void setHoofdGroep(GroepenSrvPojo hoofdGroep) {
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
    public PersonenSrvPojo getPersoon() {
        return persoon;
    }

    public void setPersoon(PersonenSrvPojo persoon) {
        this.persoon = persoon;
    }

    @Override
    public Integer getPublicGroep() {
        return publicGroep;
    }

    public void setPublicGroep(Integer publicGroep) {
        this.publicGroep = publicGroep;
    }

    @Override
    public boolean equals(Object objGroep) {
        GroepenSrvPojo groep = (GroepenSrvPojo)objGroep;
        
        return this.pk_id.equals(groep.getPk_id());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + (this.pk_id != null ? this.pk_id.hashCode() : 0);
        return hash;
    }
    
    public GroepenSrvPojo(){
        
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

    public GroepenSrvPojo(Groepen groepen){
        this.hoofdGroep = groepen.getHoofdGroep()!=null ? new GroepenSrvPojo(groepen.getHoofdGroep()): null;
        this.naam = groepen.getNaam();
        this.negatief = groepen.getNegatief();
        this.omschrijving = groepen.getOmschrijving();
        this.persoon = groepen.getPersoon()!=null ? new PersonenSrvPojo(groepen.getPersoon()) : null;
        this.pk_id = groepen.getPk_id();
        this.publicGroep = groepen.getPublicGroep();
        this.closed = groepen.getClosed();
        this.category = groepen.getCategory();
    }
}
