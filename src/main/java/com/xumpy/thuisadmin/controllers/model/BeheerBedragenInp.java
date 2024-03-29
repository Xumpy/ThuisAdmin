/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.controllers.model;

import com.xumpy.thuisadmin.dao.model.RekeningenDaoPojo;
import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Nico
 */
@Component
@Scope("session")
public class BeheerBedragenInp implements Serializable{
    private Integer offset;
    private RekeningenDaoPojo rekening;
    private String zoekOpdracht;
    private Boolean validAccountyBedrag;
    private Boolean invalidAccouting;

    private BigDecimal courantValue;

    public BigDecimal getCourantValue() {
        return courantValue;
    }

    public void setCourantValue(BigDecimal courantValue) {
        this.courantValue = courantValue;
    }

    public RekeningenDaoPojo getRekening() {
        return rekening;
    }

    public void setRekening(RekeningenDaoPojo rekening) {
        this.rekening = rekening;
    }

    public String getZoekOpdracht() {
        return zoekOpdracht;
    }

    public void setZoekOpdracht(String zoekOpdracht) {
        this.zoekOpdracht = zoekOpdracht;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
    
    public BeheerBedragenInp(){
        this.offset = 0;
        this.validAccountyBedrag = false;
        this.invalidAccouting = false;
    }

    public Boolean getInvalidAccouting() {
        return invalidAccouting;
    }

    public void setInvalidAccouting(Boolean invalidAccouting) {
        this.invalidAccouting = invalidAccouting;
    }

    public Boolean getValidAccountyBedrag() {
        return validAccountyBedrag;
    }

    public void setValidAccountyBedrag(Boolean validAccountyBedrag) {
        this.validAccountyBedrag = validAccountyBedrag;
    }
}
