/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.security.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.xumpy.thuisadmin.domain.Personen;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author nicom
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class UserInfo implements Serializable{
    private Personen persoon;
    private InvoiceType invoiceType;

    public Personen getPersoon() {
        return persoon;
    }

    public void setPersoon(Personen persoon) {
        this.persoon = persoon;
    }

    public void updateBean(Personen persoon){
        this.persoon = persoon;
    }

    public InvoiceType getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(InvoiceType invoiceType) {
        this.invoiceType = invoiceType;
    }

    public List<Object> getInvoiceTypes(){
        return Arrays.asList(InvoiceType.values());
    }
}
