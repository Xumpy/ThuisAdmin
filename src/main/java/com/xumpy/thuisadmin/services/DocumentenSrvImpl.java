/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services;

import com.xumpy.thuisadmin.dao.DocumentenDaoImpl;
import com.xumpy.thuisadmin.model.db.Bedragen;
import com.xumpy.thuisadmin.model.db.Documenten;
import com.xumpy.thuisadmin.model.view.DocumentenReport;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nico
 */

@Service
public class DocumentenSrvImpl implements DocumentenSrv{

    @Autowired
    private DocumentenDaoImpl documentenDao;
    
    @Override
    @Transactional(readOnly=false)
    public void save(Bedragen bedragen) {
        documentenDao.save(bedragen);
    }

    @Override
    @Transactional(readOnly=false)
    public void update(Bedragen bedragen) {
        documentenDao.update(bedragen);
    }

    @Override
    @Transactional(readOnly=false)
    public void delete(Bedragen bedragen) {
        documentenDao.delete(bedragen);
    }

    @Override
    @Transactional
    public List<DocumentenReport> fetchDocumentenReport() {
        return documentenDao.fetchDocumentenReport();
    }

    @Override
    @Transactional
    public Documenten fetchDocument(Integer documentId){
        Documenten documenten = documentenDao.fetchDocument(documentId);
        return documenten;
    }
    
    @Override
    public List<DocumentenReport> fetchBedragDocumenten(Integer bedragId){
        return documentenDao.fetchBedragDocumenten(bedragId);
    }
}
