/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.model.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Nico
 */
@Entity
@Table(name="TA_BEDRAG_DOCUMENTEN")
public class Documenten {
    @Id
    @Column(name="PK_ID")
    private Integer pk_id;
    
    @ManyToOne
    @JoinColumn(name="FK_BEDRAG_ID")
    private Bedragen bedrag;
    
    @Column(name="OMSCHRIJVING")
    private String omschrijving;
    
    @Column(name="DOCUMENT")
    @Lob
    private byte[] document;

    @Column(name="DOCUMENT_NAAM")
    private String document_naam;
    
    @Column(name="DOCUMENT_MIME")
    private String document_mime;

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
    
    public Integer getPk_id() {
        return pk_id;
    }

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    public Bedragen getBedrag() {
        return bedrag;
    }

    public void setBedrag(Bedragen bedrag) {
        this.bedrag = bedrag;
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
}