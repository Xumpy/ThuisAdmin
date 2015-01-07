/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.model.view;

import java.io.Serializable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Nico
 */
@Component
@Scope("session")
public class OverzichtGroepBedragenInp implements Serializable{
    private Integer typeGroepId;
    private Integer typeGroepKostOpbrengst;
    private String beginDatum;
    private String eindDatum;

    public Integer getTypeGroepId() {
        return typeGroepId;
    }

    public void setTypeGroepId(Integer typeGroepId) {
        this.typeGroepId = typeGroepId;
    }

    public Integer getTypeGroepKostOpbrengst() {
        return typeGroepKostOpbrengst;
    }

    public void setTypeGroepKostOpbrengst(Integer typeGroepKostOpbrengst) {
        this.typeGroepKostOpbrengst = typeGroepKostOpbrengst;
    }

    public String getBeginDatum() {
        return beginDatum;
    }

    public void setBeginDatum(String beginDatum) {
        this.beginDatum = beginDatum;
    }

    public String getEindDatum() {
        return eindDatum;
    }

    public void setEindDatum(String eindDatum) {
        this.eindDatum = eindDatum;
    }
}
