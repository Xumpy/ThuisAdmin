/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.controllers.model;

import java.util.Date;

/**
 *
 * @author nicom
 */
public class NieuwRekening {
    private Integer pk_id;
    private String waarde;
    private String naam;
    private Date laatst_bijgewerkt;
    private String bank;
    private String rekeningNr;
    private Boolean closed;

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getRekeningNr() {
        return rekeningNr;
    }

    public void setRekeningNr(String rekeningNr) {
        this.rekeningNr = rekeningNr;
    }

    public Integer getPk_id() {
        return pk_id;
    }

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    public String getWaarde() {
        return waarde;
    }

    public void setWaarde(String waarde) {
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

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }
}
