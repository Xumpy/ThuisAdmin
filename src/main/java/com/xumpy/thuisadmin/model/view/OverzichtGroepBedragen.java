/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.model.view;

import com.xumpy.thuisadmin.model.db.Bedragen;
import com.xumpy.thuisadmin.model.db.Groepen;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Nico
 */
@Entity
public class OverzichtGroepBedragen implements Serializable{
    @Id
    private Integer pk_id;
    private String type_naam;
    private BigDecimal bedrag;
    private String datum;
    private String omschrijving;

    public Integer getPk_id() {
        return pk_id;
    }

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    public String getType_naam() {
        return type_naam;
    }

    public void setType_naam(String type_naam) {
        this.type_naam = type_naam;
    }

    public BigDecimal getBedrag() {
        return bedrag;
    }

    public void setBedrag(BigDecimal bedrag) {
        this.bedrag = bedrag;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }
    
    public void setWithBedrag(Bedragen bedrag){
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd"); 
        
        this.pk_id = bedrag.getPk_id();
        this.type_naam = bedrag.getGroep().getNaam();
        this.bedrag = bedrag.getBedrag();
        this.datum = dt.format(bedrag.getDatum());
        this.omschrijving = bedrag.getOmschrijving();

    }
}
