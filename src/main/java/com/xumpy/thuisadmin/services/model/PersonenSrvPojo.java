/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services.model;

import com.xumpy.thuisadmin.dao.model.PersonenDaoPojo;
import com.xumpy.thuisadmin.domain.Personen;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

/**
 *
 * @author nicom
 */
public class PersonenSrvPojo implements Personen{
    private Integer pk_id;
    private String naam;
    private String voornaam;
    private String username;
    private String md5_password;

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
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.pk_id != null ? this.pk_id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PersonenSrvPojo other = (PersonenSrvPojo) obj;
        if (this.pk_id != other.pk_id && (this.pk_id == null || !this.pk_id.equals(other.pk_id))) {
            return false;
        }
        return true;
    }
    
    public PersonenSrvPojo(){
        
    }
    
    public PersonenSrvPojo(Personen personen){
        this.md5_password = personen.getMd5_password();
        this.naam = personen.getNaam();
        this.pk_id = personen.getPk_id();
        this.username = personen.getUsername();
        this.voornaam = personen.getVoornaam();
    }
    
    public void set_password(String password){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.md5_password = (new HexBinaryAdapter()).marshal(md.digest(md5_password.getBytes())).toLowerCase();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(PersonenDaoPojo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
