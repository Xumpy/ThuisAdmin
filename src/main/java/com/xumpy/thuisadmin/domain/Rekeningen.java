/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.domain;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author nicom
 */
public interface Rekeningen {
    Date getLaatst_bijgewerkt();
    String getNaam();
    Personen getPersoon();
    Integer getPk_id();
    BigDecimal getWaarde();
}
