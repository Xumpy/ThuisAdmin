package com.xumpy.thuisadmin.controllers.model;

import com.xumpy.thuisadmin.domain.Groepen;

/**
 * Created by nico on 02/10/2018.
 */
public class GroepenCtrlPojo implements Groepen{
    private Integer pk_id;
    private GroepenCtrlPojo hoofdGroep;
    private String naam;
    private String omschrijving;
    private Integer negatief;
    private PersonenCtrlPojo persoon;
    private String codeId;
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
    public GroepenCtrlPojo getHoofdGroep() {
        return hoofdGroep;
    }

    public void setHoofdGroep(GroepenCtrlPojo hoofdGroep) {
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
    public PersonenCtrlPojo getPersoon() {
        return persoon;
    }

    public void setPersoon(PersonenCtrlPojo persoon) {
        this.persoon = persoon;
    }

    @Override
    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    @Override
    public Integer getPublicGroep() {
        return publicGroep;
    }

    public void setPublicGroep(Integer publicGroep) {
        this.publicGroep = publicGroep;
    }

    @Override
    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    @Override
    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public GroepenCtrlPojo(){}
    public GroepenCtrlPojo(Groepen groepen){
        this.pk_id = groepen.getPk_id();
        this.hoofdGroep = groepen.getHoofdGroep() != null ? new GroepenCtrlPojo(groepen.getHoofdGroep()) : null;
        this.naam = groepen.getNaam();
        this.omschrijving = groepen.getOmschrijving();
        this.negatief = groepen.getNegatief();
        this.persoon = groepen.getPersoon() != null ? new PersonenCtrlPojo(groepen.getPersoon()) : null;
        this.codeId = groepen.getCodeId();
        this.publicGroep = groepen.getPublicGroep();
        this.closed = groepen.getClosed();
        this.category = groepen.getCategory();
    }
}
