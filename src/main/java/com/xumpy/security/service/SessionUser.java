/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.security.service;

/**
 *
 * @author nicom
 */
public interface SessionUser {
    public com.xumpy.thuisadmin.model.db.Personen getPersonen();
    public void setPersonen(com.xumpy.thuisadmin.model.db.Personen personen);
}
