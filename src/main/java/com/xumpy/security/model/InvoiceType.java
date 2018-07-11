package com.xumpy.security.model;

/**
 * Created by nico on 06/07/2018.
 */
public enum InvoiceType {
    PERSONAL("Personal"),
    PROFESSIONAL("Professional"),
    BOTH("Both");

    private String type;
    InvoiceType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
