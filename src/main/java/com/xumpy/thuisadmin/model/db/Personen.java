/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.model.db;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import org.hibernate.validator.constraints.NotEmpty;
/**
 *
 * @author Nico
 */
@Entity
@Table(name="TA_PERSONEN")
public class Personen implements Serializable{
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMd5_password() {
        return md5_password;
    }

    public void setMd5_password(String md5_password) {
        this.md5_password = md5_password;
    }
    
    public void set_password(String password){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.md5_password = (new HexBinaryAdapter()).marshal(md.digest(md5_password.getBytes())).toLowerCase();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Personen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
