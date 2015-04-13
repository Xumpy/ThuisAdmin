/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao;

import com.xumpy.thuisadmin.dao.model.BedragenDaoPojo;
import com.xumpy.thuisadmin.dao.model.DocumentenDaoPojo;
import com.xumpy.thuisadmin.controllers.model.DocumentenReport;
import com.xumpy.thuisadmin.domain.Documenten;
import java.util.List;

/**
 *
 * @author Nico
 */
public interface DocumentenDao {
    void save(Documenten document);
    void update(Documenten document);
    void delete(Documenten document);
    public Integer getNewPkId();
    
    public List<DocumentenReport> fetchDocumentenReport();
    
    public Documenten fetchDocument(Integer documentId);
    public List<Documenten> fetchDocumentByBedrag(Integer bedragId);
}
