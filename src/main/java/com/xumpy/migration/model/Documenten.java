/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.migration.model;

/**
 *
 * @author nicom
 */
public class Documenten {
    private Integer pk_id;
    private Integer fk_bedrag_id;
    private String omschrijving;
    private byte[] document;
    private String document_naam;
    private String document_mime;

    public Integer getPk_id() {
        return pk_id;
    }

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    public Integer getFk_bedrag_id() {
        return fk_bedrag_id;
    }

    public void setFk_bedrag_id(Integer fk_bedrag_id) {
        this.fk_bedrag_id = fk_bedrag_id;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    public String getDocument_naam() {
        return document_naam;
    }

    public void setDocument_naam(String document_naam) {
        this.document_naam = document_naam;
    }

    public String getDocument_mime() {
        return document_mime;
    }

    public void setDocument_mime(String document_mime) {
        this.document_mime = document_mime;
    }
}
