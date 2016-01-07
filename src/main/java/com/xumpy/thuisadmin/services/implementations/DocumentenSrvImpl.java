/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services.implementations;

import com.xumpy.thuisadmin.dao.implementations.DocumentenDaoImpl;
import com.xumpy.thuisadmin.controllers.model.DocumentenReport;
import com.xumpy.thuisadmin.domain.Documenten;
import com.xumpy.thuisadmin.services.DocumentenSrv;
import com.xumpy.thuisadmin.services.model.DocumentenSrvPojo;
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
    @Transactional(readOnly=false, value="transactionManager")
    public void save(Documenten document) {
        DocumentenSrvPojo documentenSrvPojo = new DocumentenSrvPojo(document);
        
        if (documentenSrvPojo.getPk_id() == null){
            documentenSrvPojo.setPk_id(documentenDao.getNewPkId());
            documentenDao.save(documentenSrvPojo);
        } else {
            documentenDao.update(documentenSrvPojo);
        }
    }

    @Override
    @Transactional(readOnly=false, value="transactionManager")
    public void update(Documenten document) {
        documentenDao.update(new DocumentenSrvPojo(document));
    }

    @Override
    @Transactional(readOnly=false, value="transactionManager")
    public void delete(Documenten document) {
        documentenDao.delete(new DocumentenSrvPojo(document));
    }

    @Override
    @Transactional(value="transactionManager")
    public List<DocumentenReport> fetchDocumentenReport() {
        return documentenDao.fetchDocumentenReport();
    }

    @Override
    @Transactional(value="transactionManager")
    public Documenten fetchDocument(Integer documentId){
        return documentenDao.fetchDocument(documentId);
    }
    
    @Override
    @Transactional(value="transactionManager")
    public List<Documenten> fetchDocumentByBedrag(Integer bedragId){
        return documentenDao.fetchDocumentByBedrag(bedragId);
    }
}
