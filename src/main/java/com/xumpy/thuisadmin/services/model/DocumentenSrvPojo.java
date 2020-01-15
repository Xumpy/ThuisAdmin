/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services.model;

import com.xumpy.thuisadmin.domain.Documenten;

/**
 *
 * @author nicom
 */
public class DocumentenSrvPojo implements Documenten {
    private Integer pk_id;
    private BedragenSrvPojo bedrag;
    private String omschrijving;
    private byte[] document;
    private String document_naam;
    private String document_mime;
    private String yukiDocumentId;

    @Override
    public Integer getPk_id() {
        return pk_id;
    }

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    @Override
    public BedragenSrvPojo getBedrag() {
        return bedrag;
    }

    public void setBedrag(BedragenSrvPojo bedrag) {
        this.bedrag = bedrag;
    }

    @Override
    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    @Override
    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    @Override
    public String getDocument_naam() {
        return document_naam;
    }

    public void setDocument_naam(String document_naam) {
        this.document_naam = document_naam;
    }

    @Override
    public String getDocument_mime() {
        return document_mime;
    }

    public void setDocument_mime(String document_mime) {
        this.document_mime = document_mime;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + (this.pk_id != null ? this.pk_id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DocumentenSrvPojo other = (DocumentenSrvPojo) obj;
        if (this.pk_id != other.pk_id && (this.pk_id == null || !this.pk_id.equals(other.pk_id))) {
            return false;
        }
        return true;
    }
    
    public DocumentenSrvPojo(){
        
    }

    @Override
    public String getYukiDocumentId() {
        return yukiDocumentId;
    }

    public void setYukiDocumentId(String yukiDocumentId) {
        this.yukiDocumentId = yukiDocumentId;
    }

    public DocumentenSrvPojo(Documenten documenten){
        this.bedrag = documenten.getBedrag()!=null ? new BedragenSrvPojo(documenten.getBedrag()): null;
        this.document = documenten.getDocument();
        this.document_mime = documenten.getDocument_mime();
        this.document_naam = documenten.getDocument_naam();
        this.omschrijving = documenten.getOmschrijving();
        this.pk_id = documenten.getPk_id();
        this.yukiDocumentId = documenten.getYukiDocumentId();
    }
}
