/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.migration.thuisadmin.model;

/**
 *
 * @author nicom
 */
public class Groepen {
    private Integer pk_id;
    private Integer fk_hoofd_type_groep_id;
    private String naam;
    private String omschrijving;
    private Integer negatief;
    private Integer fk_persoon_id;
    private String code_id;
    private Integer publicGroep;
    private Integer yukiCategory;
    
    public Integer getPk_id() {
        return pk_id;
    }

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    public Integer getFk_hoofd_type_groep_id() {
        return fk_hoofd_type_groep_id;
    }

    public void setFk_hoofd_type_groep_id(Integer fk_hoofd_type_groep_id) {
        this.fk_hoofd_type_groep_id = fk_hoofd_type_groep_id;
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

    public Integer getFk_persoon_id() {
        return fk_persoon_id;
    }

    public void setFk_persoon_id(Integer fk_persoon_id) {
        this.fk_persoon_id = fk_persoon_id;
    }

    public String getCode_id() {
        return code_id;
    }

    public void setCode_id(String code_id) {
        this.code_id = code_id;
    }

    public Integer getPublicGroep() {
        return publicGroep;
    }

    public void setPublicGroep(Integer publicGroep) {
        this.publicGroep = publicGroep;
    }

    public Integer getYukiCategory() {
        return yukiCategory;
    }

    public void setYukiCategory(Integer yukiCategory) {
        this.yukiCategory = yukiCategory;
    }
}
