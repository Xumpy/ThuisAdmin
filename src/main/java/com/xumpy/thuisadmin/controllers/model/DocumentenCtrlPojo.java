package com.xumpy.thuisadmin.controllers.model;

import com.xumpy.thuisadmin.domain.Documenten;

public class DocumentenCtrlPojo implements Documenten {
    private BedragenCtrlPojo bedrag;
    private byte[] document;
    private String document_mime;
    private String document_naam;
    private String omschrijving;
    private Integer pk_id;

    @Override
    public BedragenCtrlPojo getBedrag() {
        return bedrag;
    }

    public void setBedrag(BedragenCtrlPojo bedrag) {
        this.bedrag = bedrag;
    }

    @Override
    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    @Override
    public String getDocument_mime() {
        return document_mime;
    }

    public void setDocument_mime(String document_mime) {
        this.document_mime = document_mime;
    }

    @Override
    public String getDocument_naam() {
        return document_naam;
    }

    public void setDocument_naam(String document_naam) {
        this.document_naam = document_naam;
    }

    @Override
    public String getOmschrijving() {
        return omschrijving;
    }

    @Override
    public Integer getPk_id() {
        return pk_id;
    }

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    public DocumentenCtrlPojo() {}

    public DocumentenCtrlPojo(Documenten documenten){
        this.bedrag = documenten.getBedrag() != null ? new BedragenCtrlPojo(documenten.getBedrag()): null;
        this.document = documenten.getDocument();
        this.document_mime = documenten.getDocument_mime();
        this.document_naam = documenten.getDocument_naam();
        this.omschrijving = documenten.getOmschrijving();
        this.pk_id = documenten.getPk_id();
    }
}
