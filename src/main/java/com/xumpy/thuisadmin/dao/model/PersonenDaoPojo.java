/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao.model;

import com.xumpy.thuisadmin.domain.Personen;
import java.io.Serializable;;
import jakarta.persistence.*;
import org.hibernate.validator.constraints.NotEmpty;
/**
 *
 * @author Nico
 */
@Entity
@Table(name="TA_PERSONEN")
public class PersonenDaoPojo implements Serializable, Personen{
    @Id
    @Column(name="PK_ID")
    private Integer pk_id;
    
    @Column(name="NAAM")
    @NotEmpty
    private String naam;
    
    @Column(name="VOORNAAM")
    @NotEmpty
    private String voornaam;

    @Column(name="USER_NAME")
    private String username;
    
    @Column(name="MD5_PASSWORD")
    private String md5_password;

    @Column(name="VAT_NUMBER")
    private String vatNumber;

    @Column(name="BUSINESS_NAME")
    private String businessName;

    @Override
    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    @Override
    public Integer getPk_id() {
        return pk_id;
    }

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    @Override
    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    @Override
    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getMd5_password() {
        return md5_password;
    }

    public void setMd5_password(String md5_password) {
        this.md5_password = md5_password;
    }

    @Override
    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public PersonenDaoPojo(){
        
    }

    public PersonenDaoPojo(Personen personen){
        this.md5_password = personen.getMd5_password();
        this.naam = personen.getNaam();
        this.pk_id = personen.getPk_id();
        this.username = personen.getUsername();
        this.voornaam = personen.getVoornaam();
        this.vatNumber = personen.getVatNumber();
        this.businessName = personen.getBusinessName();
    }
}
