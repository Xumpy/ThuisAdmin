/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.controllers.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Nico
 */
@Entity
public class NieuwDocument implements Serializable {
    @Id
    private Integer pkId;
    
    private Integer bedragId;
    private String omschrijving;
    private Date datum;
            
    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    public Integer getBedragId() {
        return bedragId;
    }

    public void setBedragId(Integer bedragId) {
        this.bedragId = bedragId;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }
}
