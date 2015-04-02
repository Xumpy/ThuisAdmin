/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.controllers.model;

import com.xumpy.thuisadmin.dao.model.BedragenDaoPojo;
import com.xumpy.thuisadmin.model.Bedragen;
import com.xumpy.thuisadmin.services.model.BedragenSrvPojo;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
    
    public OverzichtGroepBedragen(){
        
    }
    
    public OverzichtGroepBedragen(Bedragen bedrag){
        setWithBedrag(bedrag);
    }

    @Override
    public boolean equals(Object o) {
        boolean nullPk_id = false;
        boolean nullBedrag = false;
        boolean nullDatum = false ;
        boolean nullOmschrijving = false;
        boolean nullType_naam = false;
        
        OverzichtGroepBedragen overzichtGroepBedragen = (OverzichtGroepBedragen)o;
        
        nullPk_id = (this.pk_id == null && overzichtGroepBedragen.getPk_id() == null);
        nullBedrag = (this.bedrag == null && overzichtGroepBedragen.getBedrag() == null);
        nullDatum = (this.datum == null && overzichtGroepBedragen.getDatum() == null);
        nullOmschrijving = (this.omschrijving == null && overzichtGroepBedragen.getOmschrijving() == null);
        nullType_naam = (this.type_naam == null && overzichtGroepBedragen.getType_naam() == null);
        
        if ((nullPk_id || getPk_id().equals(overzichtGroepBedragen.getPk_id())) && 
            (nullBedrag || getBedrag().equals(overzichtGroepBedragen.getBedrag())) &&
            (nullDatum || getDatum().equals(overzichtGroepBedragen.getDatum())) &&
            (nullOmschrijving || getOmschrijving().equals(overzichtGroepBedragen.getOmschrijving())) &&
            (nullType_naam || getType_naam().equals(overzichtGroepBedragen.getType_naam()))){
            return true;
        } else {
            return false;
        }
    }
    
    public boolean contains(String searchText){
        if (pk_id != null && pk_id.toString().toLowerCase().contains(searchText.toLowerCase())) return true;
        if (type_naam != null && type_naam.toLowerCase().contains(searchText.toLowerCase())) return true;
        if (bedrag != null && bedrag.toString().toLowerCase().contains(searchText.toLowerCase())) return true;
        if (datum != null && datum.toLowerCase().contains(searchText.toLowerCase())) return true;
        if (omschrijving != null && omschrijving.toLowerCase().contains(searchText.toLowerCase())) return true;
        
        return false;
    }
}
