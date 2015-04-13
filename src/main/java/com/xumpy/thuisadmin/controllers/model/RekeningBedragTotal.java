/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.controllers.model;

import com.xumpy.thuisadmin.domain.Rekeningen;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Nico
 */
public class RekeningBedragTotal implements Serializable {
    private BigDecimal totaal;
    private List<Rekeningen> rekeningen;

    public List<Rekeningen> getRekeningen() {
        return rekeningen;
    }

    public void setRekeningen(List<Rekeningen> rekeningen) {
        this.rekeningen = rekeningen;
    }

    public BigDecimal getTotaal() {
        return totaal;
    }

    public void setTotaal(BigDecimal totaal) {
        this.totaal = totaal;
    }
}
