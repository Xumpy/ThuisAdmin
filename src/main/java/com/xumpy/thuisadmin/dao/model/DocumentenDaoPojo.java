/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao.model;

import com.xumpy.thuisadmin.domain.Documenten;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Nico
 */
@Entity
@Table(name="TA_BEDRAG_DOCUMENTEN")
public class DocumentenDaoPojo implements Serializable, Documenten {
    @Id
    @Column(name="PK_ID")
    private Integer pk_id;
    
    @ManyToOne
    @JoinColumn(name="FK_BEDRAG_ID")
    @NotNull
    private BedragenDaoPojo bedrag;
    
    @Column(name="OMSCHRIJVING")
    private String omschrijving;
    
    @Column(name="DOCUMENT")
    @Lob
    @NotNull
    private byte[] document;

    @Column(name="DOCUMENT_NAAM")
    @NotNull
    private String document_naam;
    
    @Column(name="DOCUMENT_MIME")
    @NotNull
    private String document_mime;
    
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
    public Integer getPk_id() {
        return pk_id;
    }

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    @Override
    public BedragenDaoPojo getBedrag() {
        return bedrag;
    }

    public void setBedrag(BedragenDaoPojo bedrag) {
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
    
    public DocumentenDaoPojo(){
        
    }
    
    public DocumentenDaoPojo(Documenten documenten){
        this.bedrag = documenten.getBedrag()!=null ? new BedragenDaoPojo(documenten.getBedrag()) : null;
        this.document = documenten.getDocument();
        this.document_mime = documenten.getDocument_mime();
        this.document_naam = documenten.getDocument_naam();
        this.omschrijving = documenten.getOmschrijving();
        this.pk_id = documenten.getPk_id();
    }
}
