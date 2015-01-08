/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services;

import com.xumpy.thuisadmin.dao.DocumentenDao;
import com.xumpy.thuisadmin.model.db.Bedragen;
import com.xumpy.thuisadmin.model.db.Documenten;
import com.xumpy.thuisadmin.model.view.DocumentenReport;
import java.util.List;
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
    private DocumentenDao documentenDao;
    
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
    public Documenten fetchDocument(Integer documentId) {
        return documentenDao.fetchDocument(documentId);
    }
    
}
