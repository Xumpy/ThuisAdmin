package com.xumpy.finances.model.excel;

/**
 * Created by nico on 27/09/2018.
 */
public class Document {
    private String documentLocations;
    private byte[] document;

    public String getDocumentLocations() {
        return documentLocations;
    }

    public void setDocumentLocations(String documentLocations) {
        this.documentLocations = documentLocations;
    }

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }
}
