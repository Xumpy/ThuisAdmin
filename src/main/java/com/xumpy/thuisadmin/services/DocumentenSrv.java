/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services;

import com.xumpy.thuisadmin.model.db.Bedragen;
import com.xumpy.thuisadmin.model.db.Documenten;
import com.xumpy.thuisadmin.model.view.DocumentenReport;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Nico
 */
public interface DocumentenSrv {
    void save(Bedragen bedragen);
    void update(Bedragen bedragen);
    void delete(Bedragen bedragen);
    
    public List<DocumentenReport> fetchDocumentenReport();
    public Documenten fetchDocument(Integer documentId);
    public List<DocumentenReport> fetchBedragDocumenten(Integer bedragId);
}
