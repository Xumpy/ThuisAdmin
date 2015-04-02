/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.security.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.xumpy.thuisadmin.dao.model.PersonenDaoPojo;
import java.io.Serializable;

/**
 *
 * @author nicom
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class UserInfo implements Serializable{
    private PersonenDaoPojo persoon;

    public PersonenDaoPojo getPersoon() {
        return persoon;
    }

    public void setPersoon(PersonenDaoPojo persoon) {
        this.persoon = persoon;
    }

    public void updateBean(PersonenDaoPojo persoon){
        this.persoon = persoon;
    }
}
