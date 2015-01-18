/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.model.db;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotEmpty;
/**
 *
 * @author Nico
 */
@Entity
@Table(name="TA_PERSONEN")
public class Personen implements Serializable {
    @Id
    @Column(name="PK_ID")
    private Integer pk_id;
    
    @Column(name="NAAM")
    @NotEmpty
    private String naam;
    
    @Column(name="VOORNAAM")
    @NotEmpty
    private String voornaam;

    public Integer getPk_id() {
        return pk_id;
    }

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }
}
