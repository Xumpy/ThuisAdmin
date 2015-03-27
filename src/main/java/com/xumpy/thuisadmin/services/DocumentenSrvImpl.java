/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services;

import com.xumpy.thuisadmin.dao.DocumentenDaoImpl;
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
    private DocumentenDaoImpl documentenDao;
    
    @Override
    @Transactional(readOnly=false)
    public void save(Documenten document) {
        if (document.getPk_id() == null){
            document.setPk_id(documentenDao.getNewPkId());
            documentenDao.save(document);
        } else {
            documentenDao.update(document);
        }
    }

    @Override
    @Transactional(readOnly=false)
    public void update(Documenten document) {
        documentenDao.update(document);
    }

    @Override
    @Transactional(readOnly=false)
    public void delete(Documenten document) {
        documentenDao.delete(document);
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
    @Transactional
    public List<Documenten> fetchDocumentByBedrag(Integer bedragId){
        return documentenDao.fetchDocumentByBedrag(bedragId);
    }
}
