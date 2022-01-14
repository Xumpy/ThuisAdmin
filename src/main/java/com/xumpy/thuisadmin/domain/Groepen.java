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
public interface Groepen {
    String getCodeId();
    Groepen getHoofdGroep();
    String getNaam();
    Integer getNegatief();
    String getOmschrijving();
    Personen getPersoon();
    Integer getPk_id();
    Integer getPublicGroep();
    Boolean getClosed();
    Integer getCategory();
}
