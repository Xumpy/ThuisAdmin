/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services;

import com.xumpy.thuisadmin.model.db.Documenten;
import com.xumpy.thuisadmin.model.view.DocumentenReport;
import java.util.List;

/**
 *
 * @author Nico
 */
public interface DocumentenSrv {
    void save(Documenten document);
    void update(Documenten document);
    void delete(Documenten document);
    
    public List<DocumentenReport> fetchDocumentenReport();
    public Documenten fetchDocument(Integer documentId);
    public List<Documenten> fetchDocumentByBedrag(Integer bedragId);
}
