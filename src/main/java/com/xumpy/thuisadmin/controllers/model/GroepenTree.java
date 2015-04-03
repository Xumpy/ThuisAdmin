/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.controllers.model;

import com.xumpy.thuisadmin.model.Groepen;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Nico
 */
public class GroepenTree  implements Serializable{
    private Groepen groep;
    private List<GroepenTree> subGroep;
    private String collapsed;
    private String selected;

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getCollapsed() {
        return collapsed;
    }

    public void setCollapsed(String collapsed) {
        this.collapsed = collapsed;
    }
    
    public Groepen getGroep() {
        return groep;
    }

    public void setGroep(Groepen groep) {
        this.groep = groep;
    }

    public List<GroepenTree> getSubGroep() {
        return subGroep;
    }

    public void setSubGroep(List<GroepenTree> subGroep) {
        this.subGroep = subGroep;
    }
}
