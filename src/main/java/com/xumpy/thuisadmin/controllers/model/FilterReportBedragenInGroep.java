/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.controllers.model;

import java.io.Serializable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author nicom
 */
@Component
@Scope("session")
public class FilterReportBedragenInGroep implements Serializable {
    private String searchTekst;

    public String getSearchTekst() {
        return searchTekst;
    }

    public void setSearchTekst(String searchTekst) {
        this.searchTekst = searchTekst;
    }
}
