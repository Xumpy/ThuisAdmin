/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.controllers.model;

import com.xumpy.thuisadmin.services.model.RekeningenSrvPojo;
import java.io.Serializable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Nico
 */
@Component
@Scope("session")
public class FinanceOverview implements Serializable {
    private RekeningenSrvPojo rekening;
    private String beginDatum;
    private String eindDatum;
    private boolean showPublicGroep;
    
    public RekeningenSrvPojo getRekening() {
        return rekening;
    }

    public void setRekening(RekeningenSrvPojo rekening) {
        this.rekening = rekening;
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

    public boolean isShowPublicGroep() {
        return showPublicGroep;
    }

    public void setShowPublicGroep(boolean showPublicGroep) {
        this.showPublicGroep = showPublicGroep;
    }
}
