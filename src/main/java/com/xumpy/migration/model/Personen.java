/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.migration.model;

import com.xumpy.thuisadmin.controllers.model.RegisterUserPage;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author nicom
 */
public class Personen {
    private Integer pk_id;
    private String naam;
    private String voornaam;
    private String username;
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
}
