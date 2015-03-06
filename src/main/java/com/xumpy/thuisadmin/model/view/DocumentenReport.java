/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.model.view;

import com.xumpy.thuisadmin.model.db.Documenten;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author Nico
 */
@Entity
@Scope("session")
public class DocumentenReport implements Serializable{
    @Id
    private Integer pk_id;
    private String typeGroep;
    private Date datum;
    private BigDecimal bedrag;
    private String omschrijving;

    public Integer getPk_id() {
        return pk_id;
    }

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    public String getTypeGroep() {
        return typeGroep;
    }

    public void setTypeGroep(String typeGroep) {
        this.typeGroep = typeGroep;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public BigDecimal getBedrag() {
        return bedrag;
    }

    public void setBedrag(BigDecimal bedrag) {
        this.bedrag = bedrag;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }
    
    public DocumentenReport(Documenten document){
        this.pk_id = document.getPk_id();
        this.datum = document.getBedrag().getDatum();
        if (document.getOmschrijving() != null){
            this.omschrijving = document.getOmschrijving();
        } else {
            this.omschrijving = document.getBedrag().getOmschrijving();
        }
        this.typeGroep = document.getBedrag().getGroep().getNaam();
        this.bedrag = document.getBedrag().getBedrag();
    }
}
