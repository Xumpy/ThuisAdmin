package com.xumpy.thuisadmin.controllers.model;

import com.xumpy.thuisadmin.domain.Personen;

/**
 * Created by nico on 01/03/2018.
 */
public class PersonenCtrlPojo implements Personen{
    private Integer pk_id;
    private String naam;
    private String voornaam;
    private String username;
    private String md5_password;
    private String vatNumber;
    private String businessName;

    @Override
    public Integer getPk_id() {
        return pk_id;
    }

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    @Override
    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    @Override
    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getMd5_password() {
        return md5_password;
    }

    @Override
    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public void setMd5_password(String md5_password) {
        this.md5_password = md5_password;
    }

    @Override
    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public PersonenCtrlPojo() {}
    public PersonenCtrlPojo(Personen personen) {
        this.md5_password = personen.getMd5_password();
        this.naam = personen.getNaam();
        this.pk_id = personen.getPk_id();
        this.username = personen.getUsername();
        this.voornaam = personen.getVoornaam();
        this.vatNumber = personen.getVatNumber();
        this.businessName = personen.getBusinessName();
    }
}
