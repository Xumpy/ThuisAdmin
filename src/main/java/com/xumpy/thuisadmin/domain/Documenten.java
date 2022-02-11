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
public interface Documenten {
    Bedragen getBedrag();
    byte[] getDocument();
    String getDocument_mime();
    String getDocument_naam();
    String getOmschrijving();
    Integer getPk_id();
    Integer getPrio();
}
