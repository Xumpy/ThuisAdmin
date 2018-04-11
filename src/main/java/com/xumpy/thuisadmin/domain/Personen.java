/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.domain;

/**
 *
 * @author nicom
 */
public interface Personen {
    String getMd5_password();
    String getNaam();
    Integer getPk_id();
    String getUsername();
    String getVoornaam();
    String getVatNumber();
}
