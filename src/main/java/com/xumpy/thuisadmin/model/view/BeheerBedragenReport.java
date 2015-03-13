/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.model.view;

import com.xumpy.thuisadmin.model.db.Bedragen;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author Nico
 */
@Entity
@Scope("session")
public class BeheerBedragenReport implements Serializable{
    
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    @Id
    private Integer pk_id;
    private String type_groep;
    private String rekening;
    private String persoon;
    private BigDecimal bedrag;
    private String datum;
    private String omschrijving;

    public Integer getPk_id() {
        return pk_id;
    }

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    public String getType_groep() {
        return type_groep;
    }

    public void setType_groep(String type_groep) {
        this.type_groep = type_groep;
    }

    public String getRekening() {
        return rekening;
    }

    public void setRekening(String rekening) {
        this.rekening = rekening;
    }

    public String getPersoon() {
        return persoon;
    }

    public void setPersoon(String persoon) {
        this.persoon = persoon;
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
    
    public BeheerBedragenReport(){
        
    }
    
    public BeheerBedragenReport(Bedragen bedrag){
        this.pk_id = bedrag.getPk_id();
        this.type_groep = bedrag.getGroep().getNaam();
        this.rekening = bedrag.getRekening().getNaam();
        this.persoon = bedrag.getPersoon().getVoornaam() + " " + bedrag.getPersoon().getNaam();
        this.bedrag = bedrag.getBedrag();
        this.datum = simpleDateFormat.format(bedrag.getDatum());
        this.omschrijving = bedrag.getOmschrijving();
    }
}
