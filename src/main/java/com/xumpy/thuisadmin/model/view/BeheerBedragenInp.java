/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.model.view;

import com.xumpy.thuisadmin.model.db.Rekeningen;
import java.io.Serializable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Nico
 */
@Component
@Scope("session")
public class BeheerBedragenInp implements Serializable{
    private Integer offset;
    private Rekeningen rekening;
    private String zoekOpdracht;
    
    public Rekeningen getRekening() {
        return rekening;
    }

    public void setRekening(Rekeningen rekening) {
        this.rekening = rekening;
    }

    public String getZoekOpdracht() {
        return zoekOpdracht;
    }

    public void setZoekOpdracht(String zoekOpdracht) {
        this.zoekOpdracht = zoekOpdracht;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
    
    public BeheerBedragenInp(){
        this.offset = 0;
    }
}
