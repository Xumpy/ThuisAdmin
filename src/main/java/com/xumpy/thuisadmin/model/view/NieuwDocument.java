/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.model.view;

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
    private Integer pk_id;
    
    private Integer bedrag_id;
    private String omschrijving;
    private Date datum;
            
    public Integer getPk_id() {
        return pk_id;
    }

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    public Integer getBedrag_id() {
        return bedrag_id;
    }

    public void setBedrag_id(Integer bedrag_id) {
        this.bedrag_id = bedrag_id;
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
