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
}
